package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.Syllabus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Syllabus)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface SyllabusDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param syllabusId 主键
     * @return 实例对象
     */
    Syllabus queryById(Long syllabusId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Syllabus> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param syllabus 实例对象
     * @return 对象列表
     */
    List<Syllabus> queryAll(Syllabus syllabus);

    /**
     * 新增数据
     *
     * @param syllabus 实例对象
     * @return 影响行数
     */
    int insert(Syllabus syllabus);

    /**
     * 修改数据
     *
     * @param syllabus 实例对象
     * @return 影响行数
     */
    int update(Syllabus syllabus);

    /**
     * 通过主键删除数据
     *
     * @param syllabusId 主键
     * @return 影响行数
     */
    int deleteById(Long syllabusId);

}