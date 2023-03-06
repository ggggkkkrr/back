package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.CourseReach;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (CourseReach)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-13 11:32:14
 */
public interface CourseReachDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param courseId 主键
     * @param week     主键
     * @return 实例对象
     */
    CourseReach queryById(Long courseId, Long week);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CourseReach> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param courseReach 实例对象
     * @return 对象列表
     */
    List<CourseReach> queryAll(CourseReach courseReach);

    /**
     * 新增数据
     *
     * @param courseReach 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(CourseReach courseReach) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param courseReach 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(CourseReach courseReach) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param courseId 主键
     * @param week     主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long courseId, Long week) throws DataAccessException;

}