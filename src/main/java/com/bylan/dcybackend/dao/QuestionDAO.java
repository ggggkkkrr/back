package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.bo.TaskScoreBO;
import com.bylan.dcybackend.dto.CreateQuestionDTO;
import com.bylan.dcybackend.dto.UpdateQuestionDTO;
import com.bylan.dcybackend.entity.Question;
import com.bylan.dcybackend.vo.QuestionScoreDetailVO;
import com.bylan.dcybackend.vo.QuestionVO;
import com.bylan.dcybackend.vo.ScoreDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 题目
 *
 * @author wuhuaming
 */
public interface QuestionDAO {

    /**
     * 传入和任务绑定的多个问题
     *
     * @param createQuestionDTOList 创建问题DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsertQuestion(@Param("createQuestionDTOs") List<CreateQuestionDTO> createQuestionDTOList) throws DataAccessException;

    /**
     * 传入和任务绑定的多个问题
     *
     * @param updateQuestionDTOList 修改问题DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdateQuestion(@Param("updateQuestionDTOs") List<UpdateQuestionDTO> updateQuestionDTOList) throws DataAccessException;

    /**
     * 传入多个问题id
     *
     * @param questionIds 问题id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> questionIds) throws DataAccessException;

    /**
     * 根据id获取题目分数
     *
     * @param questionId 题目id
     * @return 题目分数
     */
    Double getScoreById(@Param("questionId") Long questionId);

    /**
     * 根据任务id获取题目信息及知识点描述
     *
     * @param taskId 任务id
     * @return 题目信息
     */
    List<QuestionVO> getQuestionByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据任务id获取题目id
     *
     * @param taskId 任务id
     * @return 题目id列表
     */
    List<Long> getQuestionIdByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据任务id获取题目id
     *
     * @param taskIds 任务ids
     * @return 题目id列表
     */
    List<Long> mGetQuestionIdByTaskIds(@Param("taskIds") List<Long> taskIds);

    /**
     * 根据任务id获取题目的id、名字、分值
     *
     * @param taskId 任务id
     * @return 题目的id、名字、分值李北奥
     */
    List<ScoreDetailVO> getQuestionInfoByTaskId(@Param("taskId") Long taskId);

    /**
     * 更新平均分
     *
     * @param questionId   题目id
     * @param averageScore 平均分
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int updateAverageScore(@Param("questionId") Long questionId, @Param("averageScore") Double averageScore) throws DataAccessException;

    /**
     * 获取任务对应的题目数量
     *
     * @param taskId 任务id
     * @return 题目数量
     */
    Integer getQuestionNumByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据taskId删除却斯通
     *
     * @param taskIds 任务ids
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByTaskId(@Param("taskIds") List<Long> taskIds) throws DataAccessException;

    /**
     * 根据taskId获取题目信息
     *
     * @param taskId 任务id
     * @return 题目信息
     */
    List<QuestionScoreDetailVO> getQuestionDetailByTaskId(@Param("taskId") Long taskId);

    /**
     * 通过题目id获取题目平均分
     *
     * @param questionId 题目id
     * @return 平均分
     */
    Double getQuestionAverageScoreByQuestionId(@Param("questionId") Long questionId);

    /**
     * 通过题目id获取任务id
     *
     * @param questionId 题目id
     * @return 任务id
     */
    Long getTaskIdByQuestionId(@Param("questionId") Long questionId);

    /**
     * 通过任务和学生id获取学生分数
     *
     * @param taskId    任务id
     * @param studentId 学生id
     * @return 分数
     */
    Double getStuScoreByTaskIdAndStuId(@Param("taskId") Long taskId,
                                       @Param("studentId") String studentId);

    /**
     * 通过知识点id获取涉及的题目
     *
     * @param knowledgeId 知识点id
     * @param sectionId   教学班id
     * @param week        周次
     * @param taskType    任务类型
     * @return 题目列表
     */
    List<Question> getQuestionByKnowledgeIdAndSectionId(@Param("knowledgeId") Long knowledgeId,
                                                        @Param("sectionId") Long sectionId,
                                                        @Param("week") Long week,
                                                        @Param("taskType") Long taskType);

    /**
     * 通过知识点id获取涉及的题目
     *
     * @param knowledgeId 知识点id
     * @param sectionId   教学班id
     * @param week        周次
     * @param taskType    任务类型
     * @return 题目列表
     */
    List<Question> mGetQuestionByKnowledgeIdAndSectionId(@Param("knowledgeId") Long knowledgeId,
                                                         @Param("sectionId") Long sectionId,
                                                         @Param("week") Long week,
                                                         @Param("taskType") List<Long> taskType);

    /**
     * 根据taskId获取id、name、desc
     *
     * @param taskId 任务id
     * @return 题目列表
     */
    List<Question> getByTaskId(@Param("taskId") Long taskId);

    /**
     * 获取题目的分数和id
     *
     * @param week        周次
     * @param assertType  考核类型
     * @param knowledgeId 知识点id
     * @param status      状态
     * @param sectionId   教学班id
     * @return 题目列表
     */
    List<Question> getQuestionByAssertTypeAndWeekAndKnowId(@Param("week") Long week,
                                                           @Param("assertType") Long assertType,
                                                           @Param("knowledgeId") Long knowledgeId,
                                                           @Param("status") Long status,
                                                           @Param("sectionId") Long sectionId);


    /**
     * 获取任务分数
     *
     * @param taskIds 任务ids
     * @return 任务分数
     */
    List<TaskScoreBO> getTaskScoreByTaskIds(@Param("taskIds") List<Long> taskIds);
}
