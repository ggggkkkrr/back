package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.SectionReach;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (SectionReach)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-11 11:43:46
 */
public interface SectionReachDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param sectionId 主键
     * @param week      主键
     * @return 实例对象
     */
    SectionReach queryById(Long sectionId, Long week);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SectionReach> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sectionReach 实例对象
     * @return 对象列表
     */
    List<SectionReach> queryAll(SectionReach sectionReach);

    /**
     * 新增数据
     *
     * @param sectionReach 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(SectionReach sectionReach) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param sectionReach 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(SectionReach sectionReach) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param sectionId 主键
     * @param week      主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long sectionId, Long week) throws DataAccessException;

}