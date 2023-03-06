package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.GraduateRequirementL1;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (GraduateRequirementL1)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface GraduateRequirementL1DAO {

    /**
     * 通过ID查询单条数据
     *
     * @param l1Id 主键
     * @return 实例对象
     */
    GraduateRequirementL1 queryById(Long l1Id);

    /**
     * 通过majorId查询数据
     *
     * @param majorId 专业id
     * @return 对象列表
     */
    List<GraduateRequirementL1> queryByMajorId(Long majorId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GraduateRequirementL1> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param graduateRequirementL1 实例对象
     * @return 对象列表
     */
    List<GraduateRequirementL1> queryAll(GraduateRequirementL1 graduateRequirementL1);

    /**
     * 新增数据
     *
     * @param graduateRequirementL1 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(GraduateRequirementL1 graduateRequirementL1) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param graduateRequirementL1 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(GraduateRequirementL1 graduateRequirementL1) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param l1Id 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long l1Id) throws DataAccessException;

}