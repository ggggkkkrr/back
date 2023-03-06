package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.UpdateCourseScheduleDTO;
import com.bylan.dcybackend.entity.CourseSchedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (CourseSchedule)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface CourseScheduleDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param scheduleId 主键
     * @return 实例对象
     */
    CourseSchedule queryById(Long scheduleId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CourseSchedule> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param courseSchedule 实例对象
     * @return 对象列表
     */
    List<CourseSchedule> queryAll(CourseSchedule courseSchedule);

    /**
     * 通过courseId批量查询
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<CourseSchedule> mGetCourseScheduleByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param courseSchedule 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(CourseSchedule courseSchedule) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param courseScheduleList 实例对象列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<CourseSchedule> courseScheduleList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param courseSchedule 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(CourseSchedule courseSchedule) throws DataAccessException;


    /**
     * 批量修改数据
     *
     * @param updateCourseScheduleDTOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateCourseScheduleDTOs") List<UpdateCourseScheduleDTO> updateCourseScheduleDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param scheduleId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long scheduleId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param scheduleIds 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> scheduleIds) throws DataAccessException;

    /**
     * 通过courseId批量删除数据
     *
     * @param courseId 课程id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;
}