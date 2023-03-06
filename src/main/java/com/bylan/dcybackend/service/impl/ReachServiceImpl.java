package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.bo.ReachBO;
import com.bylan.dcybackend.bo.StuReachBO;
import com.bylan.dcybackend.bo.StuReachDetailBO;
import com.bylan.dcybackend.bo.TaskScoreBO;
import com.bylan.dcybackend.dao.*;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.entity.*;
import com.bylan.dcybackend.service.ReachService;
import com.bylan.dcybackend.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bylan.dcybackend.domain.Constant.Public.TWO_BITS;

/**
 * 达成度服务实现
 *
 * @author Rememorio
 */
@Service
public class ReachServiceImpl implements ReachService {

    private static final Logger log = LogManager.getLogger(ReachServiceImpl.class);

    @Autowired
    AssessStructDAO assessStructDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    QuestionScoreDAO questionScoreDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    TaskDAO taskDAO;

    @Autowired
    QuestionKnowledgePointDAO questionKnowledgePointDAO;

    @Autowired
    KnowledgePointDAO knowledgePointDAO;

    @Autowired
    KnowledgeReachDAO knowledgeReachDAO;

    @Autowired
    IloDAO iloDAO;

    @Autowired
    IloReachDAO iloReachDAO;

    @Autowired
    SectionTeacherDAO sectionTeacherDAO;
    @Autowired
    SectionDAO sectionDAO;

    @Autowired
    SectionReachDAO sectionReachDAO;

    @Autowired
    CourseDAO courseDAO;

    @Autowired
    CurriculumDAO curriculumDAO;

    @Autowired
    CourseReachDAO courseReachDAO;

    /**
     * 根据教学班id获取课程的考核项目权重
     *
     * @param sectionId 教学班id
     * @return 课程的考核项目权重
     */
    private List<AssessStruct> getCourseWeight(Long sectionId) {
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        return assessStructDAO.mGetAssessStructByCourseId(courseId);
    }

    /**
     * 根据教学班id获取课程的考核项目id
     *
     * @param sectionId 教学班id
     * @return 课程的考核项目id
     */
    private List<Long> getCourseWeightId(Long sectionId) {
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        return assessStructDAO.mGetAssessStructIdByCourseId(courseId);
    }

    /**
     * 根据权重和分数计算加权得分
     *
     * @param weights 权重列表
     * @param scores  分数列表
     * @return 加权得分
     */
    private Double getWeightedScore(List<Double> weights, List<Double> scores) {
        // 两个列表长度必须相等，且权重必须有效
        // 对于权重归一化处理
        double sum = weights.stream().mapToDouble(Double::doubleValue).sum();
        double factor = 1.0 / sum;

        double score = 0.0;
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) * factor);
            score += weights.get(i) * scores.get(i);
        }
        return score;
    }

    /**
     * 计算某个班某一周的知识点达成度
     *
     * @param knowledgeId 知识点id
     * @param sectionId   教学班id
     * @param week        周次
     * @return 达成度BO
     */
    private ReachBO calculateKnowledgeReachBySectionIdAndWeek(Long knowledgeId, Long sectionId, Long week) {
        // 先查询数据库，如果有这一周的达成度，就直接返回
        KnowledgeReach knowledgeReach = knowledgeReachDAO.queryById(knowledgeId, week, Constant.Reach.SECTION, String.valueOf(sectionId));
        if (knowledgeReach != null) {
            return new ReachBO(knowledgeReach.getKnowledgeReachScore(), true);
        }
        // 否则再根据不同的任务类型计算知识点达成度
        List<AssessStruct> assessStructList = getCourseWeight(sectionId);

        List<Double> validWeights = new ArrayList<>();
        List<Double> validScores = new ArrayList<>();
        boolean isAllScored = true;
        for (AssessStruct assessStruct : assessStructList) {
            // 获取与该知识点相关的题目
            List<Question> questionList = questionDAO.getQuestionByKnowledgeIdAndSectionId(knowledgeId, sectionId, week, assessStruct.getAssessStructId());
            if (questionList.size() == 0) {
                log.info("知识点{}对应的题目数量为0", knowledgeId);
                continue;
            }
            double score = 0.0;
            for (Question question : questionList) {
                // 如果平均分不为空，就直接用平均分来计算
                if (question.getQuestionAverageScore() != null) {
                    score += question.getQuestionAverageScore() / question.getQuestionScore();
                } else {
                    // 如果平均分字段为空，就去计算整个班的题目平均分
                    Integer stuNum = studentDAO.getStuNumBySectionId(sectionId);
                    Integer scoreNum = questionScoreDAO.getScoreCountByQuestionId(sectionId);
                    // 如果缺，既不纳入计算，也不存入数据库
                    if (!stuNum.equals(scoreNum)) {
                        isAllScored = false;
                        continue;
                    }
                    // 如果全部人都打分了，就将计算的平均分存入这个字段
                    Double avgScore = questionScoreDAO.getAverageScoreByQuestionId(question.getQuestionId());
                    if (avgScore == null) {
                        avgScore = 0.0d;
                    }
                    score += avgScore / question.getQuestionScore();
                    questionDAO.updateAverageScore(question.getQuestionId(), avgScore);
                }
            }
            validWeights.add(assessStruct.getAssessStructWeight());
            validScores.add(score / questionList.size());
        }
        // 如果没有一个成绩，说明这个知识点没有被任何一个考核项目考察到，那就返回一个无效的负值
        if (validScores.size() == 0) {
            return ReachBO.invalidReachBO();
        }

        // 计算加权得分，并转换为百分制，这样在知识点往上的所有层级都是百分制
        double score = 100 * getWeightedScore(validWeights, validScores);
        // 根据flag判断是否存进数据库
        if (isAllScored) {
            knowledgeReach = new KnowledgeReach(knowledgeId, week, Constant.Reach.SECTION, String.valueOf(sectionId), score);
            try {
                knowledgeReachDAO.insert(knowledgeReach);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入知识点达成度失败 堆栈信息: {}; 数据信息: {}", e, knowledgeReach);
            }
        }
        return new ReachBO(score, isAllScored);
    }

    /**
     * 计算某个班某一周的ILO达成度
     *
     * @param iloId     ILO的id
     * @param sectionId 教学班id
     * @param week      周次
     * @return 达成度BO
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private ReachBO calculateIloReachBySectionIdAndWeek(Long iloId, Long sectionId, Long week) throws InvalidPropertiesFormatException {
        // 先查询数据库，如果有这一周的达成度，就直接返回
        IloReach iloReach = iloReachDAO.queryById(iloId, week, Constant.Reach.SECTION, String.valueOf(sectionId));
        if (iloReach != null) {
            return new ReachBO(iloReach.getIloReachScore(), true);
        }
        // 否则先看对应知识点的达成度
        // 获取与该ILO相关的知识点
        List<KnowledgePoint> knowledgePointList = knowledgePointDAO.mGetKnowledgePointByIloId(iloId);
        if (knowledgePointList.size() == 0) {
            log.error("ILO-{}对应的知识点数量为0", iloId);
            throw new InvalidPropertiesFormatException(String.format("ILO-%d对应的知识点数量为0", iloId));
        }

        List<Double> weights = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        boolean isAllScored = true;
        for (KnowledgePoint knowledgePoint : knowledgePointList) {
            Double weight = knowledgePoint.getKnowledgeWeight();
            if (weight.equals(0.0)) {
                continue;
            }
            ReachBO reachBO = calculateKnowledgeReachBySectionIdAndWeek(knowledgePoint.getKnowledgeId(), sectionId, week);
            // 如果这个知识点无效，说明它没有被考察到，照样按照0分计算
            if (!reachBO.isValid()) {
                reachBO.setScore(0.0);
            }
            weights.add(weight);
            scores.add(reachBO.getScore());
            // 如果有一个没有被完全打分，那就不存数据库
            if (!reachBO.getIsAllScored()) {
                isAllScored = false;
            }
        }
        // 如果没有一个成绩，说明这个ILO没有被任何一个知识点考察到，那就返回一个无效的负值
        if (scores.size() == 0) {
            return ReachBO.invalidReachBO();
        }

        // 计算加权得分
        double score = getWeightedScore(weights, scores);
        // 根据flag判断是否存进数据库
        if (isAllScored) {
            iloReach = new IloReach(iloId, week, Constant.Reach.SECTION, String.valueOf(sectionId), score);
            try {
                iloReachDAO.insert(iloReach);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入ILO达成度失败 堆栈信息: {}; 数据信息: {}", e, iloReach);
            }
        }
        return new ReachBO(score, isAllScored);
    }

    /**
     * 计算某个班某一周的达成度
     *
     * @param sectionId 教学班id
     * @param week      周次
     * @return 达成度BO
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private ReachBO calculateSectionReachByWeek(Long sectionId, Long week) throws InvalidPropertiesFormatException {
        // 先查询数据库，如果有这一周的达成度，就直接返回
        SectionReach sectionReach = sectionReachDAO.queryById(sectionId, week);
        if (sectionReach != null) {
            return new ReachBO(sectionReach.getSectionReachScore(), true);
        }
        // 否则先看对应ILO的达成度
        Section section = sectionDAO.queryById(sectionId);
        Long courseId = section.getCourseId();
        // 获取与该课程相关的ILO
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        if (iloList.size() == 0) {
            log.error("教学班id = {} 所在课程id = {} 对应的ILO数量为0", sectionId, courseId);
            throw new InvalidPropertiesFormatException(String.format("教学班id = %d 所在课程id = %d 对应的ILO数量为0", sectionId, courseId));
        }

        List<Double> weights = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        boolean isAllScored = true;
        for (Ilo ilo : iloList) {
            Double weight = ilo.getIloWeight();
            if (weight.equals(0.0)) {
                continue;
            }
            ReachBO reachBO = calculateIloReachBySectionIdAndWeek(ilo.getIloId(), sectionId, week);
            // 如果这个ILO无效，说明它没有被考察到，照样按照0分计算
            if (!reachBO.isValid()) {
                reachBO.setScore(0.0);
            }
            weights.add(weight);
            scores.add(reachBO.getScore());
            if (!reachBO.getIsAllScored()) {
                isAllScored = false;
            }
        }
        // 如果没有一个成绩，说明这门课程没被任何一个ILO考察到，那就返回一个无效的负值
        if (scores.size() == 0) {
            return ReachBO.invalidReachBO();
        }

        // 计算加权得分
        double score = getWeightedScore(weights, scores);
        // 根据flag判断是否存进数据库
        if (isAllScored) {
            sectionReach = new SectionReach(sectionId, week, score);
            try {
                sectionReachDAO.insert(sectionReach);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入教学班达成度失败 堆栈信息: {}; 数据信息: {}", e, sectionReach);
            }
        }
        return new ReachBO(score, isAllScored);
    }

    /**
     * 计算某个课程某一周的知识点达成度
     *
     * @param knowledgeId 知识点id
     * @param courseId    课程id
     * @param week        周次
     * @return 达成度BO
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private ReachBO calculateKnowledgeReachByCourseIdAndWeek(Long knowledgeId, Long courseId, Long week) throws InvalidPropertiesFormatException {
        // 先查询数据库，如果有这一周的达成度，就直接返回
        KnowledgeReach knowledgeReach = knowledgeReachDAO.queryById(knowledgeId, week, Constant.Reach.COURSE, String.valueOf(courseId));
        if (knowledgeReach != null) {
            return new ReachBO(knowledgeReach.getKnowledgeReachScore(), true);
        }
        // 否则计算该课程对应的教学班的达成度
        List<Section> sectionList = sectionDAO.getSectionByCourseId(courseId);
        if (sectionList.size() == 0) {
            log.error("课程id = {} 对应的教学班数量为0", courseId);
            throw new InvalidPropertiesFormatException(String.format("课程id = %d 对应的教学班数量为0", courseId));
        }

        List<Double> scores = new ArrayList<>();
        boolean isAllScored = true;
        for (Section section : sectionList) {
            ReachBO reachBO = calculateKnowledgeReachBySectionIdAndWeek(knowledgeId, section.getSectionId(), week);
            // 如果有一个班有成绩是无效的，那就直接跳过，相当于把成绩有效的班扩展到整个课程
            // 加大加粗楷体
            if (!reachBO.isValid()) {
                continue;
            }
            scores.add(reachBO.getScore());
            if (!reachBO.getIsAllScored()) {
                isAllScored = false;
            }
        }

        OptionalDouble avgScore = scores.stream().mapToDouble(Double::doubleValue).average();
        // 如果没有一个成绩，说明这个知识点没有被任何一个考核项目考察到，那就返回一个无效的负值
        if (avgScore.isEmpty()) {
            return ReachBO.invalidReachBO();
        }

        double score = avgScore.getAsDouble();
        // 根据flag判断是否存进数据库
        if (isAllScored) {
            knowledgeReach = new KnowledgeReach(knowledgeId, week, Constant.Reach.COURSE, String.valueOf(courseId), score);
            try {
                knowledgeReachDAO.insert(knowledgeReach);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入知识点达成度失败 堆栈信息: {}; 数据信息: {}", e, knowledgeReach);
            }
        }
        return new ReachBO(score, isAllScored);
    }

    /**
     * 计算某个课程某一周的ILO达成度
     *
     * @param iloId    ILO的id
     * @param courseId 课程id
     * @param week     周次
     * @return 达成度BO
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private ReachBO calculateIloReachByCourseIdAndWeek(Long iloId, Long courseId, Long week) throws InvalidPropertiesFormatException {
        // 先查询数据库，如果有这一周的达成度，就直接返回
        IloReach iloReach = iloReachDAO.queryById(iloId, week, Constant.Reach.COURSE, String.valueOf(courseId));
        if (iloReach != null) {
            return new ReachBO(iloReach.getIloReachScore(), true);
        }
        // 否则先看对应知识点的达成度
        // 获取与该ILO相关的知识点
        List<KnowledgePoint> knowledgePointList = knowledgePointDAO.mGetKnowledgePointByIloId(iloId);
        if (knowledgePointList.size() == 0) {
            log.error("ILO-{}对应的知识点数量为0", iloId);
            throw new InvalidPropertiesFormatException(String.format("ILO-%d对应的知识点数量为0", iloId));
        }

        List<Double> weights = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        boolean isAllScored = true;
        for (KnowledgePoint knowledgePoint : knowledgePointList) {
            Double weight = knowledgePoint.getKnowledgeWeight();
            if (weight.equals(0.0)) {
                continue;
            }
            ReachBO reachBO = calculateKnowledgeReachByCourseIdAndWeek(knowledgePoint.getKnowledgeId(), courseId, week);
            // 如果这个知识点无效，说明它没有被考察到，照样按照0分计算
            if (!reachBO.isValid()) {
                reachBO.setScore(0.0);
            }
            weights.add(weight);
            scores.add(reachBO.getScore());
            // 如果有一个没有被完全打分，那就不存数据库
            if (!reachBO.getIsAllScored()) {
                isAllScored = false;
            }
        }
        // 如果没有一个成绩，说明这个ILO没有被任何一个知识点考察到，那就返回一个无效的负值
        if (scores.size() == 0) {
            return ReachBO.invalidReachBO();
        }


        // 计算加权得分
        double score = getWeightedScore(weights, scores);
        // 根据flag判断是否存进数据库
        if (isAllScored) {
            iloReach = new IloReach(iloId, week, Constant.Reach.COURSE, String.valueOf(courseId), score);
            try {
                iloReachDAO.insert(iloReach);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入ILO达成度失败 堆栈信息: {}; 数据信息: {}", e, iloReach);
            }
        }
        return new ReachBO(score, isAllScored);
    }

    /**
     * 计算某门课程某一周的达成度
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 达成度BO
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private ReachBO calculateCourseReachByWeek(Long courseId, Long week) throws InvalidPropertiesFormatException {
        // 先查询数据库，如果有这一周的达成度，就直接返回
        CourseReach courseReach = courseReachDAO.queryById(courseId, week);
        if (courseReach != null) {
            return new ReachBO(courseReach.getCourseReachScore(), true);
        }
        // 否则先看对应ILO的达成度

        // Q: 这里为什么不直接用教学班达成度来取平均呢？
        // A: 是因为计算course的知识点、ILO达成度的时候，把没有考核到的section忽略掉，直接将考核到的section的成绩扩展到整个course
        // 所以直接用section达成度来取平均，结果上看似乎没有什么意义，因为有的ILO或者知识点都没有对应的分数

        // 获取与该课程相关的ILO
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        if (iloList.size() == 0) {
            log.error("课程id = {} 对应的ILO数量为0", courseId);
            throw new InvalidPropertiesFormatException(String.format("课程id = %d 对应的ILO数量为0", courseId));
        }

        List<Double> weights = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        boolean isAllScored = true;
        for (Ilo ilo : iloList) {
            Double weight = ilo.getIloWeight();
            if (weight.equals(0.0)) {
                continue;
            }
            ReachBO reachBO = calculateIloReachByCourseIdAndWeek(ilo.getIloId(), courseId, week);
            // 如果这个ILO无效，说明它没有被考察到，照样按照0分计算
            if (!reachBO.isValid()) {
                reachBO.setScore(0.0);
            }
            weights.add(weight);
            scores.add(reachBO.getScore());
            if (!reachBO.getIsAllScored()) {
                isAllScored = false;
            }
        }
        // 如果没有一个成绩，说明这门课程没被任何一个ILO考察到，那就返回一个无效的负值
        if (scores.size() == 0) {
            return ReachBO.invalidReachBO();
        }

        // 计算加权得分
        double score = getWeightedScore(weights, scores);
        // 根据flag判断是否存进数据库
        if (isAllScored) {
            courseReach = new CourseReach(courseId, week, score);
            try {
                courseReachDAO.insert(courseReach);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入课程达成度失败 堆栈信息: {}; 数据信息: {}", e, courseReach);
            }
        }
        return new ReachBO(score, isAllScored);
    }

    /**
     * 计算课程预期达成度
     *
     * @param courseId 课程id
     * @return 课程预期达成度
     */
    private Double calculateCourseExpectedReach(Long courseId) throws InvalidPropertiesFormatException {
        // 获取与该课程相关的ILO
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        if (iloList.size() == 0) {
            log.error("课程id = {} 对应的ILO数量为0", courseId);
            throw new InvalidPropertiesFormatException(String.format("课程id = %d 对应的ILO数量为0", courseId));
        }
        List<Double> iloExpectedScores = new ArrayList<>();
        List<Double> iloWeights = new ArrayList<>();
        for (Ilo ilo : iloList) {
            Long iloId = ilo.getIloId();
            // 获取与该ILO相关的知识点
            List<KnowledgePoint> knowledgePointList = knowledgePointDAO.mGetKnowledgePointByIloId(iloId);
            if (knowledgePointList.size() == 0) {
                log.error("ILO-{}对应的知识点数量为0", iloId);
                throw new InvalidPropertiesFormatException(String.format("ILO-%d对应的知识点数量为0", iloId));
            }
            List<Double> knowledgeWeights = new ArrayList<>();
            List<Double> knowledgeExpectedScores = new ArrayList<>();
            for (KnowledgePoint knowledgePoint : knowledgePointList) {
                knowledgeWeights.add(knowledgePoint.getKnowledgeWeight());
                knowledgeExpectedScores.add(knowledgePoint.getExpectedScore());
            }
            // 计算加权预期期望分数
            iloExpectedScores.add(getWeightedScore(knowledgeWeights, knowledgeExpectedScores));
            iloWeights.add(ilo.getIloWeight());
        }
        return getWeightedScore(iloWeights, iloExpectedScores);
    }

    /**
     * 计算某个班截止到某一周的知识点达成度
     *
     * @param sectionId 教学班id
     * @param week      周次
     * @return 教学班达成度
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private Double getAccumulateSectionReachByWeek(Long sectionId, Long week) throws InvalidPropertiesFormatException {
        List<Double> sectionReachList = new ArrayList<>();
        for (int i = 1; i <= week; i++) {
            ReachBO reachBO = calculateSectionReachByWeek(sectionId, (long) i);
            sectionReachList.add(reachBO.getScore());
        }
        return sectionReachList.stream().reduce(0.0, Double::sum) / week;
    }

    /**
     * 计算某门课程截止到某一周的知识点达成度
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 课程达成度
     * @throws InvalidPropertiesFormatException 权重设置异常
     */
    private Double getAccumulateCourseReachByWeek(Long courseId, Long week) throws InvalidPropertiesFormatException {
        List<Double> courseReachList = new ArrayList<>();
        for (int i = 1; i <= week; i++) {
            ReachBO reachBO = calculateCourseReachByWeek(courseId, (long) i);
            courseReachList.add(reachBO.getScore());
        }
        return courseReachList.stream().reduce(0.0, Double::sum) / week;
    }

    /**
     * 通过教师工号获取在授课程
     *
     * @param teacherId 教师工号
     * @return 课程ids
     */
    @Override
    public List<Long> mGetCourseIdByTeacherId(String teacherId) {
        return sectionTeacherDAO.getOpenedCourseIdByTeacherId(teacherId);
    }

    /**
     * 获取最新教学周的课程达成度
     *
     * @param courseIds 课程ids
     * @return 首页课程达成度VO
     */
    @Override
    public List<HomeCourseReachVO> mGetCourseReachByCourseId(List<Long> courseIds) {
        List<HomeCourseReachVO> courseReachVOList = new ArrayList<>();

        for (Long courseId : courseIds) {
            // 计算课程预期达成度，由于基于课程计算的达成度，每个教学班都是一样的，所以就算一个即可
            Double courseExpectedReach;
            try {
                courseExpectedReach = calculateCourseExpectedReach(courseId);
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("courseId = {}对应的ILO或者知识点数量为0", courseId);
                return courseReachVOList;
            }

            // 获取当前布置的任务中最新的周次
            Long latestWeek = taskDAO.getLatestWeekByCourseId(courseId);
            Double courseActualReach;
            try {
                // 获取课程的达成度
                courseActualReach = getAccumulateCourseReachByWeek(courseId, latestWeek);
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("请检查courseId = {}的权重设置是否合理", courseId);
                return courseReachVOList;
            }

            Course course = courseDAO.queryById(courseId);
            Curriculum curriculum = curriculumDAO.queryById(course.getCurriculumId());
            // 获取教学班的达成度
            List<HomeSectionReachVO> sectionReachVOList = new ArrayList<>();
            List<Section> sectionList = sectionDAO.getSectionByCourseId(courseId);
            sectionList.forEach(section -> {
                Double sectionReach;
                try {
                    sectionReach = getAccumulateSectionReachByWeek(section.getSectionId(), latestWeek);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.warn("请检查sectionId = {}的权重设置是否合理", section.getSectionId());
                    return;
                }
                sectionReachVOList.add(new HomeSectionReachVO(section.getSectionId(), section.getSectionName(), sectionReach));
            });

            courseReachVOList.add(new HomeCourseReachVO(courseId, curriculum.getCurriculumName(), courseActualReach, courseExpectedReach, sectionReachVOList));
        }

        return courseReachVOList;
    }

    /**
     * 填充课程进展的达成度
     *
     * @param courseProgressViewVO 课程进展
     * @param courseId             课程id
     * @param week                 周次
     */
    @Override
    public void fillCourseReach(CourseProgressViewVO courseProgressViewVO, Long courseId, Long week) {
        // 设置课程达成度
        try {
            ReachBO courseReach = calculateCourseReachByWeek(courseId, week);
            courseProgressViewVO.setCourseReach(String.format(TWO_BITS, courseReach.getScore()));
        } catch (Exception e) {
            e.printStackTrace();
            courseProgressViewVO.setCourseReach(String.format(TWO_BITS, 0.0d));
            return;
        }

        // 设置ILO达成度
        List<IloProgressViewVO> iloProgressViewVOList = courseProgressViewVO.getIloProgresses();
        if (iloProgressViewVOList == null) {
            return;
        }
        for (IloProgressViewVO iloProgressViewVO : iloProgressViewVOList) {
            try {
                ReachBO iloReach = calculateIloReachByCourseIdAndWeek(iloProgressViewVO.getIloId(), courseId, week);
                iloProgressViewVO.setIloReach(String.format(TWO_BITS, iloReach.getScore()));
            } catch (Exception e) {
                e.printStackTrace();
                iloProgressViewVO.setIloReach(String.format(TWO_BITS, 0.0d));
            }
            // 设置知识点达成度
            List<KnowledgeProgressViewVO> knowledgeProgressViewVOList = iloProgressViewVO.getKnowledgeProgresses();
            if (knowledgeProgressViewVOList == null) {
                continue;
            }
            for (KnowledgeProgressViewVO knowledgeProgressViewVO : knowledgeProgressViewVOList) {
                try {
                    ReachBO knowledgeReach = calculateKnowledgeReachByCourseIdAndWeek(knowledgeProgressViewVO.getKnowledgeId(), courseId, week);
                    knowledgeProgressViewVO.setKnowledgeReach(String.format(TWO_BITS, knowledgeReach.getScore()));
                } catch (Exception e) {
                    e.printStackTrace();
                    knowledgeProgressViewVO.setKnowledgeReach(String.format(TWO_BITS, 0.0d));
                }
            }
        }
    }

    /**
     * 获取学生在指定教学的指定考核类型达成情况
     * 如果考核类型为null 便是所有类型都计算在内
     */
    @Override
    public Double getStuReachByStudentIdAndWeekAndAssertTypeAndSectionId(String studentId, Long week, Long assertType, Long sectionId) throws InvalidPropertiesFormatException {
        // 根据sectionId获取courseId
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        // 根据courseId获取所有的ILO
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        // 根据各个ILO计算其ILO达成度的值 根据其值判断 如果没有值表示没有考核项目 将权重设置为0
        List<Double> iloWeights = new ArrayList<>();
        List<Double> iloScores = new ArrayList<>();
        if (iloList.size() == 0) {
            log.error("课程id = {} 对应的ILO数量为0", courseId);
            throw new InvalidPropertiesFormatException(String.format("课程id = %d 对应的ILO数量为0", courseId));
        }
        // 根据ILO的权重和值计算出课程的达成度 返回
        for (Ilo item : iloList) {
            StuReachBO stuReachBO = getStuIloReachByWeekAndStudentIdAndAssertType(studentId, week, assertType, item.getIloId(), sectionId);
            // 判断stuReachBO 检测该ilo是否有数据 如果有数据加入到列表中 如果没有将weight设置为0
            if (stuReachBO.isValid()) {
                iloWeights.add(item.getIloWeight());
                iloScores.add(stuReachBO.getScore());
            }
        }
        if (iloWeights.size() == 0) {
            // 该知识点下没有数据
            return -1.0d;
        } else {
            return getWeightedScore(iloWeights, iloScores);
        }
    }

    /**
     * 计算指定教学周和指定考核类型的学生个人的指定课程的ILO达成度
     *
     * @param studentId  学生学号
     * @param week       周次
     * @param assertType 考核类型
     * @param iloId      ilo的id
     * @return 学生达成度
     * @throws InvalidPropertiesFormatException 结构的缺失
     */
    private StuReachBO getStuIloReachByWeekAndStudentIdAndAssertType(String studentId, Long week, Long assertType, Long iloId, Long sectionId) throws InvalidPropertiesFormatException {
        // 根据iloId查询知识点
        List<KnowledgePoint> knowledgePointList = knowledgePointDAO.mGetKnowledgePointByIloId(iloId);
        if (knowledgePointList.size() == 0) {
            log.error("课程iloId = {} 对应的知识点数量为0", iloId);
            throw new InvalidPropertiesFormatException(String.format("课程id = %d 对应的知识点数量为0", iloId));
        }
        List<Double> knowPtWeights = new ArrayList<>();
        List<Double> knowPtScores = new ArrayList<>();
        for (KnowledgePoint item : knowledgePointList) {
            // 计算知识点的达成度
            StuReachBO stuReachBO = getStuKnowPtByWeekAndStudentIdAndAssertType(studentId, week, assertType, item.getKnowledgeId(), sectionId);
            // 判断stuReachBO 检测该ilo是否有数据 如果有数据加入到列表中 如果没有将weight设置为0
            if (stuReachBO.isValid()) {
                knowPtWeights.add(item.getKnowledgeWeight());
                knowPtScores.add(stuReachBO.getScore());
            }
        }
        if (knowPtWeights.size() == 0) {
            // 该知识点下没有数据
            return StuReachBO.invalidStuReachBO();
        } else {
            return new StuReachBO(getWeightedScore(knowPtWeights, knowPtScores));
        }

    }

    /**
     * 计算学会个人教学周指定类型的知识点达成度
     *
     * @param studentId   学生学号
     * @param week        周次
     * @param assertType  考核类型
     * @param knowledgeId 知识点id
     * @param sectionId   教学班id
     * @return 学生达成度
     */
    private StuReachBO getStuKnowPtByWeekAndStudentIdAndAssertType(String studentId, Long week, Long assertType, Long knowledgeId, Long sectionId) {
        // 根据knowpt查询questionId 只查询任务类型为assertType和状态为 公布成绩 类型的问题
        List<AssessStruct> assessStructList = getCourseWeight(sectionId);
        List<Double> assertWeight = new ArrayList<>();
        List<Double> assertScore = new ArrayList<>();
        for (AssessStruct assessStruct : assessStructList) {
            Long assessStructId = assessStruct.getAssessStructId();
            if (assertType != null && !assessStructId.equals(assertType)) {
                // 如果有指定类型 但是当前和指定的不同则跳过
                continue;
            }
            List<Question> questionList = questionDAO.getQuestionByAssertTypeAndWeekAndKnowId(week, assessStructId, knowledgeId, Constant.Task.FINISHED, sectionId);
            if (questionList.size() == 0) {
                // 如果没有题目 应该将weight的权重设置为0
                continue;
            }
            List<Double> questionWeight = new ArrayList<>();
            List<Double> questionScore = new ArrayList<>();

            questionList.forEach(item -> {
                Double score = questionScoreDAO.getQuestionScoreByStuIdAndQuestionId(studentId, item.getQuestionId());
                questionWeight.add(item.getQuestionScore());
                questionScore.add(score / item.getQuestionScore() * 100);
            });
            // 判单question的长度， 如果没有数据 返回为不参与计算
            assertWeight.add(assessStruct.getAssessStructWeight());
            assertScore.add(getWeightedScore(questionWeight, questionScore));
        }
        if (assertScore.size() == 0) {
            // 该知识点下没有数据
            return StuReachBO.invalidStuReachBO();
        } else {
            return new StuReachBO(getWeightedScore(assertWeight, assertScore));
        }
    }


    private HashMap<String, Double> getStuWeightScore(List<Double> weights, List<HashMap<String, Double>> scores) {
        // 两个列表长度必须相等
        assert weights.size() == scores.size();
        // 对于权重归一化处理
        double sum = weights.stream().mapToDouble(Double::doubleValue).sum();
        double factor = 1.0 / sum;
        // 权重必须有效
        assert !Double.isInfinite(factor) && factor >= 0.0;
        HashMap<String, Double> weightedScore = new HashMap<>();
        for (int i = 0; i < weights.size(); i++) {
            Double newWeight = weights.get(i) * factor;
            scores.get(i).forEach((key, value) -> {
                Double stuScore = weightedScore.getOrDefault(key, 0d);
                stuScore += value * newWeight;
                weightedScore.put(key, stuScore);
            });
        }
        return weightedScore;
    }


    private HashMap<String, Double> getStuAssessReach(Long sectionId, Long week, Long assessId, Long knownId) {
        List<Question> questionList = questionDAO.getQuestionByAssertTypeAndWeekAndKnowId(week, assessId, knownId, Constant.Task.FINISHED, sectionId);
        if (questionList.size() == 0) {
            return new HashMap<>(16);
        }
        List<HashMap<String, Double>> scoreMapList = new ArrayList<>();
        List<Double> weightList = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            List<QuestionScore> questScoreList = questionScoreDAO.getQuestionScoreByQuestIdAndSectionId(questionList.get(i).getQuestionId(), sectionId);
            // 这里转换为Map类型 统一计算的接口
            HashMap<String, Double> map = new HashMap<>(16);
            for (int j = 0; j < questScoreList.size(); j++) {
                map.put(questScoreList.get(j).getStudentId(), questScoreList.get(j).getQuestionScore() / questionList.get(i).getQuestionScore() * 100);
            }
            scoreMapList.add(map);
            weightList.add(questionList.get(i).getQuestionScore());
        }

        return getStuWeightScore(weightList, scoreMapList);
    }

    private HashMap<String, Double> getStuKnownReach(Long sectionId, Long week, Long knowPtId) {
        List<AssessStruct> assessStructList = getCourseWeight(sectionId);
        List<HashMap<String, Double>> scoreMapList = new ArrayList<>();
        List<Double> weightList = new ArrayList<>();
        for (int i = 0; i < assessStructList.size(); i++) {
            HashMap<String, Double> map = getStuAssessReach(sectionId, week, assessStructList.get(i).getAssessStructId(), knowPtId);
            if (map.size() != 0) {
                scoreMapList.add(map);
                weightList.add(assessStructList.get(i).getAssessStructWeight());
            }
        }
        return getStuWeightScore(weightList, scoreMapList);
    }

    private HashMap<String, Double> getStuIloReach(Long sectionId, Long week, Long iloId) throws InvalidPropertiesFormatException {
        List<KnowledgePoint> knowledgePointList = knowledgePointDAO.mGetKnowledgePointByIloId(iloId);
        if (knowledgePointList.size() == 0) {
            log.error("课程iloId = {} 对应的知识点数量为0", iloId);
            throw new InvalidPropertiesFormatException(String.format("课程id = %d 对应的知识点数量为0", iloId));
        }
        List<HashMap<String, Double>> scoreMapList = new ArrayList<>();
        List<Double> weightList = new ArrayList<>();
        knowledgePointList.forEach(knownPtItem -> {
            HashMap<String, Double> map = getStuKnownReach(sectionId, week, knownPtItem.getKnowledgeId());
            if (map.size() != 0) {
                scoreMapList.add(map);
                weightList.add(knownPtItem.getKnowledgeWeight());
            }
        });
        return getStuWeightScore(weightList, scoreMapList);
    }

    @Override
    public List<HomeStuReachVO> mGetStuReachBySectionIdAndWeek(Long sectionId, Long week) throws InvalidPropertiesFormatException {
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        List<HashMap<String, Double>> scoreMapList = new ArrayList<>();
        List<Double> iloWeights = new ArrayList<>();
        for (Ilo iloItem : iloList) {
            HashMap<String, Double> map = getStuIloReach(sectionId, week, iloItem.getIloId());
            if (map.size() != 0) {
                scoreMapList.add(map);
                iloWeights.add(iloItem.getIloWeight());
            }
        }
        HashMap<String, Double> map = getStuWeightScore(iloWeights, scoreMapList);
        List<HomeStuReachVO> homeStuReachVOList = new ArrayList<>();
        List<TaskSumVO> studentList = studentDAO.getStuInfoBySectionId(sectionId);
        studentList.forEach(item -> {
            HomeStuReachVO temp = new HomeStuReachVO();
            temp.setStudentId(item.getStudentId());
            temp.setName(item.getName());
            temp.setActualReach(map.getOrDefault(item.getStudentId(), 0.0d));
            homeStuReachVOList.add(temp);
        });
        return homeStuReachVOList;
    }


    @Override
    public StuReachDetailVO mGetStuReachDetailBySectionIdAndWeek(Long sectionId, Long week, Long assertType) {
        // 题目的类型
        List<StuReachDetailHeaderVO> taskList = taskDAO.getBySectionIdAndAssertType(sectionId, week, assertType, Constant.Task.FINISHED);
        if (taskList.size() == 0) {
            StuReachDetailVO temp = new StuReachDetailVO();
            temp.setStuReachDetailHeaderVOList(new ArrayList<>());
            temp.setStuReachDetailDataVOList(new ArrayList<>());
            return temp;
        }
        List<Long> taskIds = new ArrayList<>();
        taskList.forEach(item -> taskIds.add(item.getTaskId()));
        // 获取教学班每个每个学生每个任务的得分
        List<StuReachDetailBO> stuReachDetailBOList = questionScoreDAO.getStuTaskScoreByTaskIds(taskIds);
        HashMap<String, HashMap<Long, Double>> stuReachDetailMap = new HashMap<>();
        stuReachDetailBOList.forEach(item -> {
            HashMap<Long, Double> scoreMap = stuReachDetailMap.computeIfAbsent(item.getStudentId(), k -> new HashMap<>());
            scoreMap.put(item.getTaskId(), item.getTaskScore());
        });

        List<TaskScoreBO> taskScoreBOList = questionDAO.getTaskScoreByTaskIds(taskIds);
        HashMap<Long, Double> taskScore = new HashMap<>();
        taskScoreBOList.forEach(item -> taskScore.put(item.getTaskId(), item.getScore()));
        // 获取班级同学名单
        List<TaskSumVO> studentList = studentDAO.getStuInfoBySectionId(sectionId);
        List<StuReachDetailDataVO> stuReachDetailDataVOList = new ArrayList<>();

        for (TaskSumVO each : studentList) {
            StuReachDetailDataVO stuReachDetailVO = new StuReachDetailDataVO();
            stuReachDetailVO.setStudentName(each.getName());
            stuReachDetailVO.setStudentId(each.getStudentId());
            List<StuScoreVO> stuScoreVOList = new ArrayList<>();
            for (StuReachDetailHeaderVO taskItem : taskList) {
                StuScoreVO stuScoreVO = new StuScoreVO();
                stuScoreVO.setTaskId(taskItem.getTaskId());
                stuScoreVO.setScore(stuReachDetailMap.get(each.getStudentId()).get(taskItem.getTaskId()) / taskScore.get(taskItem.getTaskId()) * 100);
                stuScoreVOList.add(stuScoreVO);

            }
            stuReachDetailVO.setStuScoreVOList(stuScoreVOList);
            stuReachDetailDataVOList.add(stuReachDetailVO);
        }
        StuReachDetailVO stuReachDetailVO = new StuReachDetailVO();
        stuReachDetailVO.setStuReachDetailHeaderVOList(taskList);
        stuReachDetailVO.setStuReachDetailDataVOList(stuReachDetailDataVOList);
        return stuReachDetailVO;
    }

    @Override
    public Long getLatestWeekBySectionId(Long sectionId, Long assessType) {
        return taskDAO.getLatestWeekBySectionIdAndStatus(sectionId, assessType, Constant.Task.FINISHED);
    }


}
