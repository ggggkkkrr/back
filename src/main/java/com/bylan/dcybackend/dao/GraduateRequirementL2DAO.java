package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.GraduateRequirementL2;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (GraduateRequirementL2)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface GraduateRequirementL2DAO {

    /**
     * 通过ID查询单条数据
     *
     * @param l2Id 主键
     * @return 实例对象
     */
    GraduateRequirementL2 queryById(Long l2Id);

    /**
     * 通过l1Id查询数据
     *
     * @param l1Id l1Id
     * @return 对象列表
     */
    List<GraduateRequirementL2> queryByL1Id(Long l1Id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GraduateRequirementL2> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param graduateRequirementL2 实例对象
     * @return 对象列表
     */
    List<GraduateRequirementL2> queryAll(GraduateRequirementL2 graduateRequirementL2);

    /**
     * 新增数据
     *
     * @param graduateRequirementL2 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(GraduateRequirementL2 graduateRequirementL2) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param graduateRequirementL2 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(GraduateRequirementL2 graduateRequirementL2) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param l2Id 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long l2Id) throws DataAccessException;

}