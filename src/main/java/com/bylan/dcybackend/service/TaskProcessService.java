package com.bylan.dcybackend.service;

import com.bylan.dcybackend.bo.CheckQuestScoreBO;
import com.bylan.dcybackend.dto.CreateEvaluationDTO;
import com.bylan.dcybackend.dto.CreateQuestScoreDTO;
import com.bylan.dcybackend.dto.CreateQuestionDTO;
import com.bylan.dcybackend.dto.UpdateQuestionDTO;
import com.bylan.dcybackend.entity.Task;
import com.bylan.dcybackend.entity.TaskScore;
import com.bylan.dcybackend.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author wuhuaming
 */
public interface TaskProcessService {

    /**
     * 批量新建任务
     *
     * @param tasks 任务DTO
     * @return 成功
     */
    Boolean mCreateTasks(List<Task> tasks);

    /**
     * 获取一个教学班的任务
     *
     * @param section 教学班id
     * @return 任务列表
     */
    List<TaskVO> mGetTaskBySessionId(Long section);

    /**
     * 批量更新任务
     *
     * @param tasks 任务列表
     * @return 成功
     */
    Boolean mUpdateTask(List<Task> tasks);

    /**
     * 批量删除任务
     *
     * @param taskIds 任务id
     * @return 成功
     */
    Boolean mDeleteTask(List<Long> taskIds);

    /**
     * 批量新建题目
     *
     * @param createQuestionDTOList 题目列表
     * @return 成功
     */
    Boolean mCreateQuestion(List<CreateQuestionDTO> createQuestionDTOList);

    /**
     * 批量更新题目
     *
     * @param updateQuestionDTOList 题目列表
     * @return 成功
     */
    Boolean mUpdateQuestion(List<UpdateQuestionDTO> updateQuestionDTOList);

    /**
     * 批量删除题目
     *
     * @param deleteIds 题目id
     * @return 成功
     */
    Boolean mDeleteQuestion(List<Long> deleteIds);

    /**
     * 批量新建题目分数
     *
     * @param createQuestScoreDTOList 题目分数列表
     * @return 成功
     */
    Boolean mCreateQuestionScore(List<CreateQuestScoreDTO> createQuestScoreDTOList);

    /**
     * 批量新建任务分数
     *
     * @param taskScores 任务分数列表
     * @return 成功
     */
    Boolean mCreateTaskScore(List<TaskScore> taskScores);

    /**
     * 通过课程id获取教学班
     *
     * @param courseId 课程id
     * @return 教学班列表
     */
    List<SectionVO> getSectionIdByCourseId(Long courseId);

    /**
     * 批量检查题目分数是否合法
     *
     * @param createQuestScoreDTOList 新建题目分数列表
     * @param exist                   存在
     * @return 合法
     */
    CheckQuestScoreBO mCheckQuestScore(List<CreateQuestScoreDTO> createQuestScoreDTOList, Boolean exist);

    /**
     * 通过课程id批量获取知识点
     *
     * @param courseId 课程id
     * @return 知识点列表
     */
    List<KnowlListVO> mGetKnowlByCourseId(Long courseId);

    /**
     * 通过任务id获取题目
     *
     * @param taskId 任务id
     * @return 题目列表
     */
    List<QuestionVO> getQuestionByTaskId(Long taskId);

    /**
     * 通过教学班id获取指定任务种类
     *
     * @param sectionId 教学班id
     * @param week      周次
     * @return 任务种类
     */
    List<TaskCategoryVO> getTaskCategoryBySectionId(Long sectionId, Long week);

    /**
     * 获取任务分数统计
     *
     * @param sectionId 教学班id
     * @param taskId    任务id
     * @return 任务分数统计
     */
    List<TaskSumVO> getTaskSummary(Long sectionId, Long taskId);

    /**
     * 获取分数系数
     *
     * @param taskId 任务id
     * @return 分数系数
     */
    List<ScoreDetailVO> getScoreDetail(Long taskId);

    /**
     * 更新教学评价
     *
     * @param createEvaluationDTOList 创建教学评价
     * @return 成功
     */
    Boolean mUpdateEvaluation(List<CreateEvaluationDTO> createEvaluationDTOList);

    /**
     * 通过任务id获取ddl
     *
     * @param taskId 任务id
     * @return ddl
     */
    Date getTaskDeadlineById(Long taskId);

    /**
     * 更新任务状态
     *
     * @param taskIds 任务id
     * @param status  目标状态
     * @return 成功
     */
    Boolean mUpdateTaskStatus(List<Long> taskIds, Long status);

    /**
     * 获取任务问题总数
     *
     * @param taskId 任务id
     * @return 问题数量
     */
    Integer getQuestionNum(Long taskId);

    /**
     * 获取任务状态
     *
     * @param taskId 任务id
     * @return 状态
     */
    Long getTaskStatusByTaskId(Long taskId);

    /**
     * 通过题目id获取任务状态
     *
     * @param questionId 题目id
     * @return 状态
     */
    Long getTaskStatusByQuestionId(Long questionId);

    /**
     * 获取题目分数
     *
     * @param studentId 学生学号
     * @param taskId    任务id
     * @return 题目分数细则
     */
    List<QuestionScoreDetailVO> getQuestionScoreByStuIdAndTaskId(String studentId, Long taskId);

    /**
     * 批量更新题目分数
     *
     * @param createQuestScoreDTOList 题目分数列表
     * @return 成功
     */
    Boolean mUpdateQuestionScore(List<CreateQuestScoreDTO> createQuestScoreDTOList);

    /**
     * 获取任务分数
     *
     * @param studentId 学生学号
     * @param sectionId 教学班id
     * @return 任务分数
     */
    List<TaskScoreDetailVO> getStuTaskScoreDetail(String studentId, Long sectionId);

    /**
     * 获取学生某次任务提交的图片
     *
     * @param filePath 答案存储的路径
     * @param response 响应
     */
    void getTaskAnswer(String filePath, HttpServletResponse response);

    /**
     * 校验是否所有题目都已经打分
     *
     * @param taskId 任务di
     * @return 是否全部打分
     */
    Boolean checkMarkAllStu(Long taskId);

    /**
     * 获取单个任务所有学生所有题目的分数
     *
     * @param taskId 任务id
     * @return 学生分数
     */
    List<StuQuestInfoVO> getStuQuestByTaskId(Long taskId);

    /**
     * 上传任务附件
     *
     * @param files 文件
     * @return 上传之后的文件名
     */
    List<String> uploadTaskFiles(MultipartFile[] files);


    /**
     * 根据任务id删除任务服务器上的附件
     *
     * @param taskId 任务id
     * @return 成功
     */
    Boolean deleteTaskFilesByTaskId(Long taskId);

    /**
     * 下载任务附件
     *
     * @param filename 文件名
     * @param response 响应
     */
    void downloadTaskFile(String filename, HttpServletResponse response);

    /**
     * 提交作业
     *
     * @param files     文件列表
     * @param sectionId 教学班id
     * @param taskId    任务id
     * @param studentId 学生id
     * @return 成功
     */
    Boolean submitHomework(MultipartFile[] files, Long sectionId, Long taskId, String studentId);

    /**
     * 打包下载学生附件
     *
     * @param sectionId 教学班id
     * @param taskId    任务id
     * @param studentId 学生id
     * @param response  响应
     */
    void zipHomework(Long sectionId, Long taskId, String studentId, HttpServletResponse response);

    /**
     * 获取任务详细信息
     *
     * @param taskId 任务id
     * @return 任务信息
     */
    TaskVO getTaskDetailByTaskId(Long taskId);
}
