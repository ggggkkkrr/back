package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.bo.StuReachDetailBO;
import com.bylan.dcybackend.dto.CreateQuestScoreDTO;
import com.bylan.dcybackend.entity.QuestionScore;
import com.bylan.dcybackend.vo.QuestionScoreDetailVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

/**
 * 题目得分
 *
 * @author wuhuaming
 */
public interface QuestionScoreDAO {

    /**
     * 批量插入实体
     *
     * @param createQuestScoreDTOList 创建题目得分DTO
     * @return 影响行数
     * @throws DataAccessException 数据库错误
     */
    int mCreateQuestScore(@Param("createQuestScoreDTOs") List<CreateQuestScoreDTO> createQuestScoreDTOList) throws DataAccessException;

    /**
     * 查询一个学生所有分数
     *
     * @param stuId  学生学号
     * @param taskId 任务id
     * @return 分数列表
     */
    List<Double> getScoreByStuIdAndTaskId(@Param("studentId") String stuId, @Param("taskId") Long taskId);

    /**
     * 根据题目编号获得所有分数
     *
     * @param questionId 题目id
     * @return 分数列表
     */
    List<Double> getScoreByQuestionId(@Param("questionId") Long questionId);

    /**
     * 根据题目编号获得所有分数的数量
     *
     * @param questionId 题目id
     * @return 分数数量
     */
    Integer getScoreCountByQuestionId(@Param("questionId") Long questionId);

    /**
     * 通过学生和任务id获取学生的题目得分
     *
     * @param studentId 学生学号
     * @param taskId    任务id
     * @return 题目得分
     */
    List<QuestionScoreDetailVO> getQuestionScoreByStuIdAndTaskId(String studentId, Long taskId);

    /**
     * 通过学生和题目id获取学生的题目得分
     *
     * @param studentId  学生学号
     * @param questionId 题目id
     * @return 题目得分
     */
    Double getQuestionScoreByStuIdAndQuestionId(@Param("studentId") String studentId, @Param("questionId") Long questionId);

    /**
     * 通过学生分数和题目id来判断是否已经存在
     *
     * @param studentId  学生学号
     * @param questionId 题目id
     * @return 是否存在
     */
    Integer getByStuIdAndQuestionId(@Param("studentId") String studentId, @Param("questionId") Long questionId);

    /**
     * 更新题目得分
     *
     * @param createQuestScoreDTOList 题目得分列表
     * @return 影响行数
     * @throws DataAccessException 数据库错误
     */
    int updateQuestionScore(List<CreateQuestScoreDTO> createQuestScoreDTOList) throws DataAccessException;

    /**
     * 获取题目平均分
     *
     * @param questionId 题目id
     * @return 平均分
     */
    Double getAverageScoreByQuestionId(@Param("questionId") Long questionId);

    /**
     * 获取任务分数
     *
     * @param studentId 学生id
     * @param taskId    任务id
     * @return 任务分数
     */
    Double getTaskScoreByStuIdAndTaskId(@Param("studentId") String studentId,
                                        @Param("taskId") Long taskId);

    /**
     * 获取记录的数量
     *
     * @param taskId
     * @return
     */
    Integer getRecordNumByTaskId(@Param("taskId") Long taskId);

    /**
     * 通过taskId获取所有学生所有题目分数
     * @param taskId
     * @return
     */
    List<QuestionScore> getByTaskId(@Param("taskId")Long taskId);

    /**
     * 获取任务的分数并且按学生、任务id进行分组
     *
     * @param taskIds
     * @return
     */
    List<StuReachDetailBO> getStuTaskScoreByTaskIds(List<Long> taskIds);

    /**
     * 获取该教学班各学生的id和成绩
     *
     * @param questionId
     * @param sectionId
     * @return
     */
    List<QuestionScore> getQuestionScoreByQuestIdAndSectionId(@Param("questionId")Long questionId,
                                                                  @Param("sectionId")Long sectionId);
}
