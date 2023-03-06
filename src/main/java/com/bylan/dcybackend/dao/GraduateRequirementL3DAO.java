package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.GraduateRequirementL3;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (GraduateRequirementL3)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface GraduateRequirementL3DAO {

    /**
     * 通过ID查询单条数据
     *
     * @param l3Id 主键
     * @return 实例对象
     */
    GraduateRequirementL3 queryById(Long l3Id);

    /**
     * 通过l2Id查询数据
     *
     * @param l2Id l2Id
     * @return 对象列表
     */
    List<GraduateRequirementL3> queryByL2Id(Long l2Id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GraduateRequirementL3> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param graduateRequirementL3 实例对象
     * @return 对象列表
     */
    List<GraduateRequirementL3> queryAll(GraduateRequirementL3 graduateRequirementL3);

    /**
     * 新增数据
     *
     * @param graduateRequirementL3 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(GraduateRequirementL3 graduateRequirementL3) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param graduateRequirementL3 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(GraduateRequirementL3 graduateRequirementL3) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param l3Id 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long l3Id) throws DataAccessException;

}