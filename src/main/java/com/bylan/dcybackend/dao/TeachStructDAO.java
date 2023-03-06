package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.UpdateTeachStructDTO;
import com.bylan.dcybackend.entity.TeachStruct;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (TeachStruct)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface TeachStructDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param teachStructId 主键
     * @return 实例对象
     */
    TeachStruct queryById(Long teachStructId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TeachStruct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param teachStruct 实例对象
     * @return 对象列表
     */
    List<TeachStruct> queryAll(TeachStruct teachStruct);

    /**
     * 通过courseId批量查询
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<TeachStruct> mGetTeachStructByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param teachStruct 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(TeachStruct teachStruct) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param teachStructList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<TeachStruct> teachStructList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param teachStruct 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(TeachStruct teachStruct) throws DataAccessException;

    /**
     * 批量修改数据
     *
     * @param updateTeachStructDTOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateTeachStructDTOs") List<UpdateTeachStructDTO> updateTeachStructDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param teachStructId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long teachStructId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param teachStructIds 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> teachStructIds) throws DataAccessException;

    /**
     * 通过courseId批量删除数据
     *
     * @param courseId 课程id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;

}