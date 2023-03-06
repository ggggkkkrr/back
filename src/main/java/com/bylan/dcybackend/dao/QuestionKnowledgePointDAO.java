package com.bylan.dcybackend.dao;


import com.bylan.dcybackend.dto.CreateQuestKnowlPtDTO;
import com.bylan.dcybackend.entity.QuestionKnowledgePoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 题目-知识点
 *
 * @author wuhuaming
 */
public interface QuestionKnowledgePointDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param questionId  主键
     * @param knowledgeId 主键
     * @return 实例对象
     */
    QuestionKnowledgePoint queryById(Long questionId, Long knowledgeId);

    /**
     * 通过题目id获取相关的知识点id
     *
     * @param questionId 题目id
     * @return 知识点id
     */
    List<Long> mGetKnowledgeByQuestionId(Long questionId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<QuestionKnowledgePoint> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param questionKnowledgePoint 实例对象
     * @return 对象列表
     */
    List<QuestionKnowledgePoint> queryAll(QuestionKnowledgePoint questionKnowledgePoint);

    /**
     * 新增数据
     *
     * @param questionKnowledgePoint 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(QuestionKnowledgePoint questionKnowledgePoint) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param questionId 主键
     * @param knowlId    主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(@Param("questionId") Long questionId, @Param("knowlId") Long knowlId) throws DataAccessException;

    /**
     * 插入
     *
     * @param createQuestionDTOList 插入题目DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsertQuestKnowlPt(@Param("createQuestKnowlPtDTOs") List<CreateQuestKnowlPtDTO> createQuestionDTOList) throws DataAccessException;

    /**
     * 删除
     *
     * @param questionIds 题目id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteQuestKnowlPtByQuestId(List<Long> questionIds) throws DataAccessException;

    /**
     * 删除
     *
     * @param knowledgeIds 知识点id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteQuestKnowlPtByKnowlId(List<Long> knowledgeIds) throws DataAccessException;

}
