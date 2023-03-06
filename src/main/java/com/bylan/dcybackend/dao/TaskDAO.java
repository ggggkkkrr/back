package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.bo.CourseProgressBO;
import com.bylan.dcybackend.entity.Task;
import com.bylan.dcybackend.vo.StuReachDetailHeaderVO;
import com.bylan.dcybackend.vo.TaskCategoryVO;
import com.bylan.dcybackend.vo.TaskScoreDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
 * 任务
 *
 * @author wuhuaming
 */
public interface TaskDAO {

    /**
     * 插入任务
     *
     * @param taskList 任务列表 单个任务看成列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsertTask(List<Task> taskList) throws DataAccessException;

    /**
     * 根绝教学班编号获得该教学班所有任务列表
     *
     * @param sectionId 教学班id
     * @return 任务列表
     */
    List<Task> mGetTask(@Param("sectionId") Long sectionId);

    /**
     * 批量更新
     *
     * @param taskList 任务列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdateTask(List<Task> taskList) throws DataAccessException;

    /**
     * 批量删除
     *
     * @param taskIds 任务id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteTask(List<Long> taskIds) throws DataAccessException;

    /**
     * 根据questionId查询deadline
     *
     * @param questionId 题目id
     * @return deadline
     */
    Date getDeadlineByQuestId(@Param("questionId") Long questionId);

    /**
     * 根据任务id获取deadline
     *
     * @param taskId 任务id
     * @return 日期
     */
    Date getDeadlineByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据sectionId和week给任务分类
     *
     * @param sectionId 教学班id
     * @param week      周次
     * @return 分类的任务
     */
    List<TaskCategoryVO> getTaskCategoryBySectionIdAndWeek(Long sectionId, Long week);

    /**
     * 获取教学班编号
     *
     * @param taskId 任务id
     * @return 教学班id
     */
    Long getSectionIdByTaskId(@Param("taskId") Long taskId);

    /**
     * 更新状态
     *
     * @param taskId 任务id
     * @param status 状态
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int updateTaskStatus(@Param("taskIds") List<Long> taskId, @Param("status") Long status) throws DataAccessException;

    /**
     * 更新状态为创建，并更新releaseTime
     *
     * @param taskId 任务id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int updateTaskReleaseTime(@Param("taskIds") List<Long> taskId) throws DataAccessException;

    /**
     * 获取任务的状态
     *
     * @param taskId 任务id
     * @return 任务状态
     */
    Long getStatusByTaskId(@Param("taskId") Long taskId);

    /**
     * 获取任务状态
     *
     * @param questionId 题目id
     * @return 任务状态
     */
    Long getStatusByQuestionId(@Param("questionId") Long questionId);

    /**
     * 获取学生个人所需自己的任务信息
     *
     * @param sectionId 教学班id
     * @param status    状态
     * @return 任务分数
     */
    List<TaskScoreDetailVO> getBySectionIdAndStatus(@Param("sectionId") Long sectionId,
                                                    @Param("status") Long status);

    /**
     * 获取当前课程的最新有任务的周次
     *
     * @param courseId 课程id
     * @return 周次
     */
    Long getLatestWeekByCourseId(@Param("courseId") Long courseId);

    /**
     * 获取题目对应的任务周次
     *
     * @param questionId 题目id
     * @return 周次
     */
    Long getWeekByQuestionId(@Param("questionId") Long questionId);

    /**
     * 通过题目id获取教学班id
     *
     * @param questionId 题目id
     * @return 教学班id
     */
    Long getSectionIdByQuestionId(@Param("questionId") Long questionId);

    /**
     * 通过课程id和周次获取课程进展
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 课程进展
     */
    List<CourseProgressBO> getCourseProgressByWeek(@Param("courseId") Long courseId, @Param("week") Long week);

    /**
     * 通过知识点id和周次获取考核的详情
     *
     * @param knowledgeId 知识点id
     * @param week        周次
     * @return 考核类型
     */
    List<Long> getAssessTypeByKnowledgeIdAndWeek(@Param("knowledgeId") Long knowledgeId, @Param("week") Long week);
    /**
     * 获取教学周指定类型的题目信息
     * @param sectionId
     * @param week
     * @param assertType
     * @return
     */
    List<StuReachDetailHeaderVO> getBySectionIdAndAssertType(@Param("sectionId")Long sectionId,
                                                             @Param("week")Long week,
                                                             @Param("assertType")Long assertType,
                                                             @Param("status")Long status);

    /**
     * 获取教学周
     *
     * @param section_id
     * @param finished
     * @return
     */
    Long getLatestWeekBySectionIdAndStatus(@Param("sectionId") Long section_id,
                                           @Param("assessType") Long assessType,
                                           @Param("status") Long finished);

    /**
     * 通过taskId获取任务信息
     *
     * @param taskId
     * @return
     */
    Task getTaskByTaskId(@Param("taskId") Long taskId);
}
