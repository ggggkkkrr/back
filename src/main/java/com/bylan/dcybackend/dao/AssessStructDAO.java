package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.UpdateAssessStructDTO;
import com.bylan.dcybackend.entity.AssessStruct;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (AssessStruct)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface AssessStructDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param assessStructId 主键
     * @return 实例对象
     */
    AssessStruct queryById(Long assessStructId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AssessStruct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param assessStruct 实例对象
     * @return 对象列表
     */
    List<AssessStruct> queryAll(AssessStruct assessStruct);

    /**
     * 通过courseId批量查询
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<AssessStruct> mGetAssessStructByCourseId(Long courseId);

    /**
     * 通过courseId批量查询考核项目id
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<Long> mGetAssessStructIdByCourseId(Long courseId);

    /**
     * 通过courseId查询主键
     *
     * @param courseId 课程id
     * @return 主键列表
     */
    List<Long> queryAllKeysByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param assessStruct 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(AssessStruct assessStruct) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param assessStructList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<AssessStruct> assessStructList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param assessStruct 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(AssessStruct assessStruct) throws DataAccessException;

    /**
     * 批量修改数据
     *
     * @param updateAssessStructDTOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateAssessStructDTOs") List<UpdateAssessStructDTO> updateAssessStructDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param assessStructId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long assessStructId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param assessStructIds 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> assessStructIds) throws DataAccessException;

    /**
     * 通过courseId删除数据
     *
     * @param courseId 课程id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;

}