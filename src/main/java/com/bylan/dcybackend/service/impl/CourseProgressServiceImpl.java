package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.bo.CourseProgressBO;
import com.bylan.dcybackend.dao.AssessStructDAO;
import com.bylan.dcybackend.dao.KnowledgePointDAO;
import com.bylan.dcybackend.dao.TaskDAO;
import com.bylan.dcybackend.entity.AssessStruct;
import com.bylan.dcybackend.service.CourseProgressService;
import com.bylan.dcybackend.vo.CourseProgressVO;
import com.bylan.dcybackend.vo.CourseProgressViewVO;
import com.bylan.dcybackend.vo.IloProgressViewVO;
import com.bylan.dcybackend.vo.KnowledgeProgressViewVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bylan.dcybackend.domain.Constant.Public.TWO_BITS;

/**
 * @author Rememorio
 * @date 2022-04-26 16:31
 */
@Service
public class CourseProgressServiceImpl implements CourseProgressService {

    private static final Logger log = LogManager.getLogger(CourseProgressServiceImpl.class);

    @Autowired
    AssessStructDAO assessStructDAO;

    @Autowired
    TaskDAO taskDAO;

    @Autowired
    KnowledgePointDAO knowledgePointDAO;

    /**
     * 获取某一周的课程进展
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 课程进展VO
     */
    @Override
    public List<CourseProgressVO> getCourseProgressByWeek(Long courseId, Long week) {
        Long latestWeek = taskDAO.getLatestWeekByCourseId(courseId);
        List<CourseProgressVO> courseProgressVOList = new ArrayList<>();
        if (week > latestWeek) {
            return courseProgressVOList;
        }

        List<CourseProgressBO> courseProgressBOList = taskDAO.getCourseProgressByWeek(courseId, week);
        // 按照ILO-知识点的顺序排序
        courseProgressBOList.sort((o1, o2) -> {
            if (!o1.getIloId().equals(o2.getIloId())) {
                return o1.getIloId().compareTo(o2.getIloId());
            }
            return o1.getKnowledgeId().compareTo(o2.getKnowledgeId());
        });

        // 获取这门课程的考核结构
        List<AssessStruct> assessStructList = assessStructDAO.mGetAssessStructByCourseId(courseId);
        // 哈希，空间换时间
        Map<Long, String> assessStructMap = new HashMap<>(assessStructList.size());
        assessStructList.forEach(item -> assessStructMap.put(item.getAssessStructId(), item.getAssessStructDesc()));

        courseProgressBOList.forEach(item -> {
            // 查询当前知识点的考核项目
            List<Long> assessTypes = taskDAO.getAssessTypeByKnowledgeIdAndWeek(item.getKnowledgeId(), week);
            if (assessTypes == null) {
                return;
            }
            // 统计不同考核项目的数量
            Map<Long, Integer> map = new HashMap<>(0);
            assessTypes.forEach(type -> {
                int count = map.getOrDefault(type, 0) + 1;
                map.put(type, count);
            });
            AtomicInteger total = new AtomicInteger(0);
            map.forEach((key, value) -> total.addAndGet(value));
            // 添加到考核项目的列表中
            List<CourseProgressVO.AssessItem> assessItemList = new ArrayList<>();
            map.forEach((key, value) -> assessItemList.add(new CourseProgressVO.AssessItem(assessStructMap.get(key), value.doubleValue() / total.get())));

            // 添加到课程进展列表中
            courseProgressVOList.add(new CourseProgressVO(item, assessItemList));
        });

        return courseProgressVOList;
    }

    /**
     * 根据courseId获取当前最新周次
     *
     * @param courseId 课程id
     * @return 最新周次
     */
    @Override
    public Long getLatestWeekByCourseId(Long courseId) {
        return taskDAO.getLatestWeekByCourseId(courseId);
    }

    /**
     * 根据courseId获取课程进展一览
     *
     * @param courseId      课程id
     * @param week          周次
     * @param isAccumulated 是否累计到当前最新周
     * @return 课程进展一览VO
     */
    @Override
    public CourseProgressViewVO getCourseProgressViewByWeek(Long courseId, Long week, Boolean isAccumulated) {
        CourseProgressViewVO courseProgressViewVO = isAccumulated ?
                knowledgePointDAO.mGetAccumulatedCourseProgressViewByCourseIdAndWeek(courseId, week) :
                knowledgePointDAO.mGetCourseProgressViewByCourseIdAndWeek(courseId, week);

        if (courseProgressViewVO == null) {
            return new CourseProgressViewVO(courseId, String.format(TWO_BITS, 0.0d), null);
        }
        // 按照ILO和知识点的顺序排序
        List<IloProgressViewVO> iloProgresses = courseProgressViewVO.getIloProgresses();
        iloProgresses.sort(Comparator.comparing(IloProgressViewVO::getIloId));
        iloProgresses.forEach(item -> {
            List<KnowledgeProgressViewVO> knowledgeProgresses = item.getKnowledgeProgresses();
            if (knowledgeProgresses == null) {
                return;
            }
            knowledgeProgresses.sort(Comparator.comparing(KnowledgeProgressViewVO::getKnowledgeId));
        });
        return courseProgressViewVO;
    }

}
