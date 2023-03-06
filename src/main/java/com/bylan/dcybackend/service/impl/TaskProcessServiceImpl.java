package com.bylan.dcybackend.service.impl;


import com.bylan.dcybackend.bo.CheckQuestScoreBO;
import com.bylan.dcybackend.dao.*;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.entity.*;
import com.bylan.dcybackend.service.TaskProcessService;
import com.bylan.dcybackend.utils.FileProcessUtil;
import com.bylan.dcybackend.utils.ZipUtil;
import com.bylan.dcybackend.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * 处理所有任务相关
 *
 * @author wuhuaming
 */
@Service
public class TaskProcessServiceImpl implements TaskProcessService {

    private static final Logger log = LogManager.getLogger(TaskProcessServiceImpl.class);

    @Autowired
    AssessStructDAO assessStructDAO;

    @Autowired
    TaskDAO taskDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    QuestionKnowledgePointDAO questionKnowledgePointDAO;

    @Autowired
    QuestionScoreDAO questionScoreDAO;

    @Autowired
    TaskScoreDAO taskScoreDAO;

    @Autowired
    SectionDAO sectionDAO;

    @Autowired
    KnowledgePointDAO knowledgePointDAO;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    KnowledgeReachDAO knowledgeReachDAO;

    @Autowired
    IloReachDAO iloReachDAO;

    @Autowired
    SectionReachDAO sectionReachDAO;

    @Autowired
    CourseReachDAO courseReachDAO;

    @Autowired
    SectionStudentDAO sectionStudentDAO;

    @Override
    public Boolean mCreateTasks(List<Task> tasks) {
        try {
            taskDAO.mInsertTask(tasks);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入task时失败 堆栈信息: {}; 数据信息: {}", e, tasks);
            return false;
        }
        return true;
    }

    @Override
    public List<TaskVO> mGetTaskBySessionId(Long sectionId) {
        List<Task> tasks = taskDAO.mGetTask(sectionId);
        // 获取这门课程的考核结构
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        List<AssessStruct> assessStructList = assessStructDAO.mGetAssessStructByCourseId(courseId);
        // 哈希，空间换时间
        Map<Long, String> assessStructMap = new HashMap<>(assessStructList.size());
        assessStructList.forEach(item -> assessStructMap.put(item.getAssessStructId(), item.getAssessStructDesc()));

        List<TaskVO> taskVOList = new ArrayList<>();
        tasks.forEach(item -> {
            TaskVO taskVO = new TaskVO(item);
            // 设置任务类型名称
            taskVO.setTaskTypeName(assessStructMap.get(taskVO.getTaskType()));
            taskVOList.add(taskVO);
        });
        return taskVOList;
    }

    @Override
    public Boolean mUpdateTask(List<Task> tasks) {
        try {
            taskDAO.mUpdateTask(tasks);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新task时失败 堆栈信息Ids: {}; 数据信息: {}", e, tasks);
            return false;
        }
        return true;
    }

    @Override
    public Boolean mDeleteTask(List<Long> taskIds) {
        try {
            taskDAO.mDeleteTask(taskIds);
            // 存储待删除的questionId
            List<Long> rmIds = questionDAO.mGetQuestionIdByTaskIds(taskIds);
            questionDAO.mDeleteByTaskId(taskIds);
            // 删除question对应的知识点
            questionKnowledgePointDAO.mDeleteQuestKnowlPtByQuestId(rmIds);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除task时失败 堆栈信息Ids: {}; 数据信息: {}", e, taskIds);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public Boolean mCreateQuestion(List<CreateQuestionDTO> createQuestionDTOList) {
        // 会使用回填 得到questionId
        try {
            questionDAO.mInsertQuestion(createQuestionDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增question时失败 堆栈信息createQuestionDTOs: {}; 数据信息: {}", e, createQuestionDTOList);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        List<CreateQuestKnowlPtDTO> createQuestKnowlPtDTOList = new ArrayList<>();
        createQuestionDTOList.forEach(each -> {
            for (Long knowledgeId : each.getKonwlId()) {
                CreateQuestKnowlPtDTO createQuestKnowlPtDTO = new CreateQuestKnowlPtDTO(each.getQuestionId(), knowledgeId);
                createQuestKnowlPtDTOList.add(createQuestKnowlPtDTO);
            }
        });

        try {
            questionKnowledgePointDAO.mInsertQuestKnowlPt(createQuestKnowlPtDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增questionKnowledge时失败 堆栈信息createQuestionDTOs: {}; 数据信息: {}", e, createQuestKnowlPtDTOList);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public Boolean mUpdateQuestion(List<UpdateQuestionDTO> updateQuestionDTOList) {
        try {
            questionDAO.mUpdateQuestion(updateQuestionDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新question失败 updateQuestionDTOs: {}; 数据信息: {}", e, updateQuestionDTOList);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        // 取出要求改的questionId 去QustKnowlPt中删除 便于后续的插入 起到更新的效果
        List<CreateQuestKnowlPtDTO> createQuestKnowlPtDTOList = new ArrayList<>();
        updateQuestionDTOList.forEach(each -> {
            if (each.getKnowlId() != null) {
                for (Long knowledgeId : each.getKnowlId()) {
                    CreateQuestKnowlPtDTO createQuestKnowlPtDTO = new CreateQuestKnowlPtDTO(each.getQuestionId(), knowledgeId);
                    createQuestKnowlPtDTOList.add(createQuestKnowlPtDTO);
                }
            }
        });
        // 判断是否
        if (createQuestKnowlPtDTOList.size() == 0) {
            return true;
        }

        List<Long> rmIds = new ArrayList<>();
        for (UpdateQuestionDTO each : updateQuestionDTOList) {
            rmIds.add(each.getQuestionId());
        }

        // 删除原有的
        try {
            questionKnowledgePointDAO.mDeleteQuestKnowlPtByQuestId(rmIds);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除QuestKnowl失败 questionKnowledgePointDAO: {}; 数据信息: {}", e, questionKnowledgePointDAO);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        // 插入新的知识点
        try {
            questionKnowledgePointDAO.mInsertQuestKnowlPt(createQuestKnowlPtDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入QuestKnowl失败 questionKnowledgePointDAO: {}; 数据信息: {}", e, questionKnowledgePointDAO);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public Boolean mDeleteQuestion(List<Long> questionIds) {
        try {
            questionDAO.mDeleteById(questionIds);
            questionKnowledgePointDAO.mDeleteQuestKnowlPtByQuestId(questionIds);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入Question失败 questionIds: {}; 数据信息: {}", e, questionIds);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Boolean mCreateQuestionScore(List<CreateQuestScoreDTO> createQuestScoreDTOList) {
        try {
            questionScoreDAO.mCreateQuestScore(createQuestScoreDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入QuestionScore失败 createQuestScoreDTOs: {}; 数据信息: {}", e, createQuestScoreDTOList);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Boolean mCreateTaskScore(List<TaskScore> taskScores) {
        try {
            taskScoreDAO.mCreateTaskScore(taskScores);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("提交作业失败 taskScores: {}; 数据信息: {}", e, taskScores);
            return false;
        }
        return true;
    }

    @Override
    public List<SectionVO> getSectionIdByCourseId(Long courseId) {
        return sectionDAO.getSectionIdByCourseId(courseId);
    }

    @Override
    public CheckQuestScoreBO mCheckQuestScore(List<CreateQuestScoreDTO> createQuestScoreDTOList, Boolean exist) {
        // 查询该题目所在任务的ddl 和 该题目的分数上限
        for (CreateQuestScoreDTO item : createQuestScoreDTOList) {
            Date deadline = taskDAO.getDeadlineByQuestId(item.getQuestionId());
            Date now = new Date();
            if (now.before(deadline)) {
                // 在deadline前打分
                return new CheckQuestScoreBO(false, "不能在截止时间前打分");
            }
            // 只有发布阶段才可以打分 创建阶段和完成阶段都不能打分
            Long status = taskDAO.getStatusByQuestionId(item.getQuestionId());
            if (!status.equals(Constant.Task.PUBLISH)) {
                return new CheckQuestScoreBO(false, "不能为非发布阶段的任务打分");
            }
            // 检验题目的分数是否合理
            Double score = questionDAO.getScoreById(item.getQuestionId());
            if (item.getQuestionScore() < 0 || item.getQuestionScore() > score) {
                return new CheckQuestScoreBO(false, "题目分数不能低于零或者超过分值");
            }
            Integer num = questionScoreDAO.getByStuIdAndQuestionId(item.getStudentId(), item.getQuestionId());
            if (exist && num.equals(0) || (!exist) && num.equals(1)) {
                return new CheckQuestScoreBO(false, "分数存在状态不合法");
            }
        }
        return new CheckQuestScoreBO(true, null);
    }

    @Override
    public List<KnowlListVO> mGetKnowlByCourseId(Long courseId) {
        return knowledgePointDAO.mGetKnowledgeByCourseId(courseId);
    }

    @Override
    public List<QuestionVO> getQuestionByTaskId(Long taskId) {
        return questionDAO.getQuestionByTaskId(taskId);
    }


    @Override
    public List<TaskCategoryVO> getTaskCategoryBySectionId(Long sectionId, Long week) {
        List<TaskCategoryVO> taskCategoryVOList = taskDAO.getTaskCategoryBySectionIdAndWeek(sectionId, week);
        // 获取课程的所有考核结构
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        List<AssessStruct> assessStructList = assessStructDAO.mGetAssessStructByCourseId(courseId);
        // 哈希，空间换时间
        Map<Long, String> map = new HashMap<>(assessStructList.size());
        assessStructList.forEach(item -> map.put(item.getAssessStructId(), item.getAssessStructDesc()));
        // 设施课程考核结构描述
        taskCategoryVOList.forEach(item -> item.setTaskTypeDesc(map.getOrDefault(item.getTaskType(), "类型不合法")));
        return taskCategoryVOList;
    }

    @Override
    public List<TaskSumVO> getTaskSummary(Long sectionId, Long taskId) {
        // 获取学号和姓名
        List<TaskSumVO> taskSumVOList = studentDAO.getStuInfoBySectionId(sectionId);
        // 查询该任务有多少道题目 便于后续统计使用
        List<Long> questionIds = questionDAO.getQuestionIdByTaskId(taskId);

        for (TaskSumVO each : taskSumVOList) {
            // 查询该学生该任务的附件和分数（汇总的分数 不一定有值 有值便是已经完成了打分）
            TaskScore taskScore = taskScoreDAO.getByStuIdAndTaskId(each.getStudentId(), taskId);
            if (taskScore == null) {
                each.setStatus(Constant.Task.UNACCOMPLISHED);
                continue;
            }
            // 学生提交的附件
            if (taskScore.getTaskPath() != null) {
                each.setPath(List.of(taskScore.getTaskPath().split(Constant.Public.SEPARATOR)));
            }
            // 教师评价
            each.setEvaluation(taskScore.getEvaluation());
            // 初始化为老师都打完分了
            each.setStatus(Constant.Task.ACCOMPLISHED);

            // 查询该学生的所有分数
            List<Double> scores = questionScoreDAO.getScoreByStuIdAndTaskId(each.getStudentId(), taskId);
            Double stuScore = scores.stream().mapToDouble(Double::doubleValue).sum();
            each.setScore(stuScore);
            if (scores.size() != questionIds.size()) {
                each.setStatus(Constant.Task.UNACCOMPLISHED);
            }

        }
        return taskSumVOList;
    }

    @Override
    public List<ScoreDetailVO> getScoreDetail(Long taskId) {
        List<ScoreDetailVO> scoreDetailVOList = questionDAO.getQuestionInfoByTaskId(taskId);
        // 获取学生的人数
        // 根据task获取section 然后根据section获取学生的人数
        Long sectionId = taskDAO.getSectionIdByTaskId(taskId);
        Integer stuNum = studentDAO.getStuNumBySectionId(sectionId);
        for (ScoreDetailVO each : scoreDetailVOList) {
            each.setStatus(Constant.Task.ACCOMPLISHED);
            // 判断question中是否已经有了question的question_average_score 没有再统计 然后将结果缓存
            Double classScore = questionDAO.getScoreById(each.getQuestionId());
            if (classScore != null) {
                each.setActualScore(classScore);
            } else {
                // 查询具体的分数
                List<Double> questScores = questionScoreDAO.getScoreByQuestionId(each.getQuestionId());
                Double scoreSum = questScores.stream().mapToDouble(Double::doubleValue).sum();
                each.setActualScore(scoreSum / stuNum);
                if (questScores.size() != stuNum) {
                    each.setStatus(Constant.Task.UNACCOMPLISHED);
                } else {
                    // 全部分数都批完了 可以直接汇总到question中
                    Double avgScore = scoreSum / stuNum;
                    try {
                        questionDAO.updateAverageScore(each.getQuestionId(), avgScore);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("更新平均分失败 questionId: {}; 数据信息: {}", e, avgScore);
                    }
                }
            }

        }
        return scoreDetailVOList;
    }

    @Override
    public Boolean mUpdateEvaluation(List<CreateEvaluationDTO> createEvaluationDTOList) {
        // 查询是否存在记录 如果没有就先插入
        try {
            List<TaskScore> taskScoreList = new ArrayList<>();
            createEvaluationDTOList.forEach(item -> {
                TaskScore taskScore = taskScoreDAO.getByStuIdAndTaskId(item.getStudentId(), item.getTaskId());
                if (taskScore == null) {
                    taskScore = new TaskScore();
                    taskScore.setStudentId(item.getStudentId());
                    taskScore.setTaskId(item.getTaskId());
                    taskScoreList.add(taskScore);
                }
            });
            if (taskScoreList.size() != 0) {
                taskScoreDAO.mCreateTaskScore(taskScoreList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("提交教师评价失败 createEvaluationDTOs: {}; 数据信息: {}", e, createEvaluationDTOList);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        // 更新task_score表
        try {
            taskScoreDAO.updateEvaluation(createEvaluationDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("提交教师评价失败 createEvaluationDTOs: {}; 数据信息: {}", e, createEvaluationDTOList);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    @Override
    public Date getTaskDeadlineById(Long taskId) {
        return taskDAO.getDeadlineByTaskId(taskId);
    }


    @Override
    public Boolean mUpdateTaskStatus(List<Long> taskIds, Long status) {
        // 更新task_score表
        try {
            taskDAO.updateTaskStatus(taskIds, status);
            // 如果是创建状态，更改releaseTime
            if (status.equals(Constant.Task.PUBLISH)) {
                taskDAO.updateTaskReleaseTime(taskIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新任务状态失败 堆栈信息: {}; 数据信息 taskId, status: {}, {} ", e, taskIds, status);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Integer getQuestionNum(Long taskId) {
        return questionDAO.getQuestionNumByTaskId(taskId);
    }

    @Override
    public Long getTaskStatusByTaskId(Long taskId) {
        return taskDAO.getStatusByTaskId(taskId);
    }

    @Override
    public Long getTaskStatusByQuestionId(Long questionId) {
        return taskDAO.getStatusByQuestionId(questionId);
    }

    @Override
    public List<QuestionScoreDetailVO> getQuestionScoreByStuIdAndTaskId(String studentId, Long taskId) {
        List<QuestionScoreDetailVO> questionScoreDetailVOList = questionDAO.getQuestionDetailByTaskId(taskId);
        for (QuestionScoreDetailVO item : questionScoreDetailVOList) {
            Double score = questionScoreDAO.getQuestionScoreByStuIdAndQuestionId(studentId, item.getQuestionId());
            item.setPersonalScore(score);
        }
        return questionScoreDetailVOList;
    }

    @Override
    public Boolean mUpdateQuestionScore(List<CreateQuestScoreDTO> createQuestScoreDTOList) {
        // 先把分数更新了
        try {
            questionScoreDAO.updateQuestionScore(createQuestScoreDTOList);
            for (CreateQuestScoreDTO createQuestScoreDTO : createQuestScoreDTOList) {
                Double score = questionDAO.getScoreById(createQuestScoreDTO.getQuestionId());
                if (score != null) {
                    Double averageScore = questionScoreDAO.getAverageScoreByQuestionId(createQuestScoreDTO.getQuestionId());
                    Long questionId = createQuestScoreDTO.getQuestionId();
                    // 更新平均分
                    questionDAO.updateAverageScore(questionId, averageScore);
                    // 更新这道题涉及的达成度
                    // 其实是先删除，等到用的时候再计算
                    Boolean deleteResult = mDeleteReachByQuestionId(questionId);
                    log.info("删除与问题{}相关达成度的操作结果：{}", questionId, deleteResult);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新分数失败 堆栈信息: {}; 数据信息: {}", e, createQuestScoreDTOList);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    /**
     * 通过题目id批量删除达成度
     *
     * @param questionId 题目id列表
     * @return 成功
     */
    private Boolean mDeleteReachByQuestionId(Long questionId) {
        // 获取其对应的周次
        Long week = taskDAO.getWeekByQuestionId(questionId);

        // 获取与其相关的知识点id
        List<Long> knowledgeIds = questionKnowledgePointDAO.mGetKnowledgeByQuestionId(questionId);
        // 删除知识点达成度
        try {
            knowledgeReachDAO.mDeleteByKnowledgeIdAndWeek(knowledgeIds, week);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除知识点达成度失败 堆栈信息: {}; 数据信息: {}, 周次：{}", e, knowledgeIds, week);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        // 获取知识点相关的iloId
        List<Long> iloIds = knowledgePointDAO.mGetIloIdByKnowledgeId(knowledgeIds);
        // 删除ILO达成度
        try {
            iloReachDAO.mDeleteByIloIdAndWeek(iloIds, week);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除知识点达成度失败 堆栈信息: {}; 数据信息: {}, 周次：{}", e, iloIds, week);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        // 获取教学班id
        Long sectionId = taskDAO.getSectionIdByQuestionId(questionId);
        try {
            sectionReachDAO.deleteById(sectionId, week);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除教学班达成度失败 堆栈信息: {}; 数据信息: {}, 周次：{}", e, sectionId, week);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        // 获取课程id
        Long courseId = sectionDAO.getCourseIdBySectionId(sectionId);
        try {
            courseReachDAO.deleteById(courseId, week);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除课程达成度失败 堆栈信息: {}; 数据信息: {}, 周次：{}", e, courseId, week);
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    @Override
    public List<TaskScoreDetailVO> getStuTaskScoreDetail(String studentId, Long sectionId) {
        // 获取该学生该课程的所有已经发布的任务信息
        List<TaskScoreDetailVO> taskScoreDetailVOList = new ArrayList<>();
        List<TaskScoreDetailVO> temp1 = taskDAO.getBySectionIdAndStatus(sectionId, Constant.Task.FINISHED);
        List<TaskScoreDetailVO> temp2 = taskDAO.getBySectionIdAndStatus(sectionId, Constant.Task.PUBLISH);
        taskScoreDetailVOList.addAll(temp1);
        taskScoreDetailVOList.addAll(temp2);
        taskScoreDetailVOList.forEach(item -> {
            TaskScore taskScore = taskScoreDAO.getByStuIdAndTaskId(studentId, item.getTaskId());
            if (taskScore != null) {
                item.setEvaluation(taskScore.getEvaluation());
                item.setSystemAnalysis(taskScore.getSystemAnalysis());
            }

            if (item.getPath() != null) {
                item.setTaskFilePath(List.of(item.getPath().split(Constant.Public.SEPARATOR)));
            }

            if (item.getTaskStatus().intValue() == Constant.Task.FINISHED) {
                Double stuScore = questionDAO.getStuScoreByTaskIdAndStuId(item.getTaskId(), studentId);
                item.setScore(stuScore);
            }
        });
        return taskScoreDetailVOList;
    }

    @Override
    public void getTaskAnswer(String filePath, HttpServletResponse response) {
        FileProcessUtil.downloadFile(filePath, response);
    }

    /**
     * 检验该个任务是否已经全部打完分
     * 题目数*学生数 == 题目分数记录数量
     *
     * @param taskId 任务id
     * @return 是否全部打分
     */
    @Override
    public Boolean checkMarkAllStu(Long taskId) {
        Integer questionNum = questionDAO.getQuestionNumByTaskId(taskId);
        Integer questionScoreNum = questionScoreDAO.getRecordNumByTaskId(taskId);
        Long sectionId = taskDAO.getSectionIdByTaskId(taskId);
        Integer stuNum = studentDAO.getStuNumBySectionId(sectionId);
        return questionNum * stuNum == questionScoreNum;
    }

    @Override
    public List<StuQuestInfoVO> getStuQuestByTaskId(Long taskId) {
        List<QuestionScore> questionScoreList = questionScoreDAO.getByTaskId(taskId);
        HashMap<String, HashMap<Long, Double>> stuMap = new HashMap<>(16);
        for (QuestionScore each : questionScoreList) {
            HashMap<Long, Double> scoreMap = stuMap.computeIfAbsent(each.getStudentId(), k -> new HashMap<>(16));
            scoreMap.put(each.getQuestionId(), each.getQuestionScore());
        }
        Long sectionId = taskDAO.getSectionIdByTaskId(taskId);
        List<UserVO> studentList = sectionStudentDAO.getStuUserVoBySectionId(sectionId);
        List<Question> questionList = questionDAO.getByTaskId(taskId);
        List<StuQuestInfoVO> stuQuestInfoVOList = new ArrayList<>();
        for (UserVO stuItem : studentList) {
            StuQuestInfoVO stuQuestInfoVO = new StuQuestInfoVO();
            stuQuestInfoVO.setName(stuItem.getUserName());
            stuQuestInfoVO.setStudentId(stuItem.getUserId());
            List<StuQuestInfoItemVO> stuQuestInfoItemVOList = new ArrayList<>();
            for (Question questItem : questionList) {
                StuQuestInfoItemVO stuQuestInfoItemVO = new StuQuestInfoItemVO();
                stuQuestInfoItemVO.setQuestionId(questItem.getQuestionId());
                stuQuestInfoItemVO.setQuestionName(questItem.getQuestionName());
                stuQuestInfoItemVO.setQuestionDesc(questItem.getQuestionDesc());
                HashMap<Long, Double> scoreMap = stuMap.get(stuItem.getUserId());
                if (scoreMap != null) {
                    Double score = scoreMap.get(questItem.getQuestionId());
                    stuQuestInfoItemVO.setQuestionScore(score);
                }
                stuQuestInfoItemVOList.add(stuQuestInfoItemVO);
            }
            stuQuestInfoVO.setStuQuestInfoItemVOList(stuQuestInfoItemVOList);
            stuQuestInfoVOList.add(stuQuestInfoVO);
        }
        return stuQuestInfoVOList;
    }

    @Override
    public List<String> uploadTaskFiles(MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = FileProcessUtil.uploadFile(file, Constant.Task.TASK, file.getOriginalFilename());
            if ("".equals(filename)) {
                deleteTaskFiles(filenames);
                return null;
            } else {
                filenames.add(filename);
            }
        }
        return filenames;
    }

    private Boolean deleteTaskFiles(List<String> filenames) {
        boolean flag = true;
        for (String filename : filenames) {
            String path = String.join(File.separator, Constant.Task.TASK, filename);
            if (!FileProcessUtil.deleteFile(path)) {
                flag = false;
                log.error("文件 {} 删除失败", filename);
            }
        }
        return flag;
    }

    @Override
    public Boolean deleteTaskFilesByTaskId(Long taskId) {
        Task task = taskDAO.getTaskByTaskId(taskId);
        String filePath = task.getTaskFilePath();
        log.info("文件路径是否为null: {}", filePath == null);
        log.info("要删除的文件所在的文件路径： {}", filePath);
        if (filePath == null) {
            return true;
        } else {
            List<String> filenames = List.of(filePath.split(Constant.Public.SEPARATOR));
            return deleteTaskFiles(filenames);
        }
    }

    @Override
    public void downloadTaskFile(String filename, HttpServletResponse response) {
        String path = String.join(File.separator, Constant.Task.TASK, filename);
        FileProcessUtil.downloadFile(path, response);
    }

    @Override
    public Boolean submitHomework(MultipartFile[] files, Long sectionId, Long taskId, String studentId) {
        List<String> filePaths = new ArrayList<>();
        // 文件夹：教学班id/任务id
        String path = String.join(File.separator, Constant.Path.HOMEWORK, String.valueOf(sectionId), String.valueOf(taskId));
        for (int i = 0; i < files.length; i++) {
            String filename = new StringBuilder().append(studentId).append(Constant.Syllabus.UNDERLINE).append(i).append(Constant.Path.JPG_FORMAT).toString();
            // 教学班id/任务id/学生学号
            String studentPath = String.join(File.separator, path, studentId);
            filename = FileProcessUtil.uploadFile(files[i], studentPath, filename);
            filePaths.add(String.join(File.separator, studentPath, filename));
        }
        CreateTaskScoreDTO createTaskScoreDTO = new CreateTaskScoreDTO(studentId, taskId, filePaths);
        // 类转换
        List<TaskScore> taskScores = new ArrayList<>();
        taskScores.add(new TaskScore(createTaskScoreDTO));
        return mCreateTaskScore(taskScores);
    }

    @Override
    public void zipHomework(Long sectionId, Long taskId, String studentId, HttpServletResponse response) {
        String srcDir = String.join(File.separator, Constant.Path.ROOT, Constant.Path.HOMEWORK, String.valueOf(sectionId), String.valueOf(taskId));
        if (studentId != null) {
            srcDir = String.join(File.separator, srcDir, studentId);
        }
        // 临时ZIP文件名
        String tempZip = FileProcessUtil.convertFilename(Constant.Path.TEMP_ZIP);
        // ROOT路径开始的文件路径
        String rootTempFile = String.join(File.separator, Constant.Path.ROOT, Constant.Path.HOMEWORK, tempZip);
        // 相对ROOT开始的文件路径
        String tempFile = String.join(File.separator, Constant.Path.HOMEWORK, tempZip);
        try {
            FileOutputStream fos = new FileOutputStream(rootTempFile);
            ZipUtil.toZip(srcDir, fos, true);
            FileProcessUtil.downloadFile(tempFile, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("文件路径{}不存在：{}", rootTempFile, e.getMessage());
        } finally {
//            log.info("删除临时压缩包状态：{}", FileProcessUtil.deleteFile(tempFile));
        }
    }

    @Override
    public TaskVO getTaskDetailByTaskId(Long taskId) {
        Task task = taskDAO.getTaskByTaskId(taskId);
        // 获取这门课程的考核结构
        Long courseId = sectionDAO.getCourseIdBySectionId(task.getSectionId());
        List<AssessStruct> assessStructList = assessStructDAO.mGetAssessStructByCourseId(courseId);
        // 哈希，空间换时间
        Map<Long, String> assessStructMap = new HashMap<>(assessStructList.size());
        assessStructList.forEach(item -> assessStructMap.put(item.getAssessStructId(), item.getAssessStructDesc()));

        TaskVO taskVO = new TaskVO(task);
        taskVO.setTaskTypeName(assessStructMap.get(taskVO.getTaskType()));
        return taskVO;
    }

}
