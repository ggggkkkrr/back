package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.Section;
import com.bylan.dcybackend.vo.SectionVO;
import com.bylan.dcybackend.vo.StuCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教学班
 *
 * @author wuhuaming
 */
public interface SectionDAO {

    /**
     * 查询教学班
     *
     * @param courseId 教学班id
     * @return 教学班VO
     */
    List<SectionVO> getSectionIdByCourseId(@Param("courseId") Long courseId);

    /**
     * 获取该学生正在开设的sectionId和courseId
     *
     * @param studentId 学生id
     * @return 学生课程VO
     */
    List<StuCourseVO> getStuCourseByStudentId(@Param("studentId") String studentId);

    /**
     * 查询教学班
     *
     * @param courseId 教学班id
     * @return 教学班列表
     */
    List<Section> getSectionByCourseId(@Param("courseId") Long courseId);

    /**
     * 通过教学班id获取课程id
     *
     * @param sectionId 教学班id
     * @return 课程id
     */
    Long getCourseIdBySectionId(@Param("sectionId") Long sectionId);

    /**
     * 通过ID查询单条数据
     *
     * @param sectionId 主键
     * @return 实例对象
     */
    Section queryById(Long sectionId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Section> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param section 实例对象
     * @return 对象列表
     */
    List<Section> queryAll(Section section);

    /**
     * 新增数据
     *
     * @param section 实例对象
     * @return 影响行数
     */
    int insert(Section section);

    /**
     * 修改数据
     *
     * @param section 实例对象
     * @return 影响行数
     */
    int update(Section section);

    /**
     * 通过主键删除数据
     *
     * @param sectionId 主键
     * @return 影响行数
     */
    int deleteById(Long sectionId);

}
