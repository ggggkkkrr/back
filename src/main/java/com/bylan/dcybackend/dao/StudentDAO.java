package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.Student;
import com.bylan.dcybackend.vo.TaskSumVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Student)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface StudentDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param studentId 主键
     * @return 实例对象
     */
    Student queryById(String studentId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Student> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param student 实例对象
     * @return 对象列表
     */
    List<Student> queryAll(Student student);

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    int insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    int update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param studentId 主键
     * @return 影响行数
     */
    int deleteById(String studentId);

    /**
     * 根据sectionId查询学生姓名和学号
     *
     * @param sectionId 教学班id
     * @return 学生姓名学号列表
     */
    List<TaskSumVO> getStuInfoBySectionId(@Param("sectionId") Long sectionId);

    /**
     * 根据sectionId获取学会的人数
     *
     * @param sectionId 教学班id
     * @return 学会的人数
     */
    Integer getStuNumBySectionId(@Param("sectionId") Long sectionId);

    /**
     * 通过学号获得姓名
     *
     * @param studentId 主键
     * @return 姓名
     */
    String getNameById(@Param("studentId") String studentId);


    /**
     * 用于判断该id是否是学生
     * @param userId
     * @return
     */
    Integer getNumById(@Param("userId")String userId);
}