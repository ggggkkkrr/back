package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.KnowledgeReach;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (KnowledgeReach)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-11 11:43:32
 */
public interface KnowledgeReachDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param knowledgeId 主键
     * @param week        主键
     * @param reachType   主键
     * @param entityId    主键
     * @return 实例对象
     */
    KnowledgeReach queryById(Long knowledgeId, Long week, Integer reachType, String entityId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<KnowledgeReach> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param knowledgeReach 实例对象
     * @return 对象列表
     */
    List<KnowledgeReach> queryAll(KnowledgeReach knowledgeReach);

    /**
     * 新增数据
     *
     * @param knowledgeReach 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(KnowledgeReach knowledgeReach) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param knowledgeReach 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(KnowledgeReach knowledgeReach) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param knowledgeId 主键
     * @param week        主键
     * @param reachType   主键
     * @param entityId    主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long knowledgeId, Long week, Integer reachType, String entityId) throws DataAccessException;

    /**
     * 通过知识点id批量删除
     *
     * @param knowledgeIds 知识点id
     * @param week         周次
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByKnowledgeIdAndWeek(@Param("knowledgeIds") List<Long> knowledgeIds, @Param("week") Long week) throws DataAccessException;

}