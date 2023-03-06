package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Course)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-07 15:03:38
 */
public interface CourseDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param courseId 主键
     * @return 实例对象
     */
    Course queryById(Long courseId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Course> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param course 实例对象
     * @return 对象列表
     */
    List<Course> queryAll(Course course);

    /**
     * 新增数据
     *
     * @param course 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(Course course) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param course 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(Course course) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param courseId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long courseId) throws DataAccessException;

}