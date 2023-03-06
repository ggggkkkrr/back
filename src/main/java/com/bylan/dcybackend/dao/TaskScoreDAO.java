package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.CreateEvaluationDTO;
import com.bylan.dcybackend.entity.TaskScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 任务得分
 *
 * @author wuhuaming
 */
public interface TaskScoreDAO {

    /**
     * 提交作业时使用 涉及字段 student_id task_id task_path
     *
     * @param taskScore 任务得分
     * @throws DataAccessException 数据库异常
     */
    void mCreateTaskScore(List<TaskScore> taskScore) throws DataAccessException;

    /**
     * 根据学号和任务编号得附件和分数
     *
     * @param studentId 学生学号
     * @param taskId    任务id
     * @return 任务分数
     */
    TaskScore getByStuIdAndTaskId(@Param("studentId") String studentId, @Param("taskId") Long taskId);

    /**
     * 更新学生分数
     *
     * @param stuId    学生学号
     * @param taskId   任务id
     * @param stuScore 学生分数
     */
    void updateScore(@Param("studentId") String stuId, @Param("taskId") Long taskId, @Param("score") Double stuScore);

    /**
     * 更新教师评价
     *
     * @param createEvaluationDTOList 新建评估DTO
     */
    void updateEvaluation(List<CreateEvaluationDTO> createEvaluationDTOList);

}
