package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.UpdateTeachDetailDTO;
import com.bylan.dcybackend.entity.TeachDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (TeachDetail)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface TeachDetailDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param teachDetailId 主键
     * @return 实例对象
     */
    TeachDetail queryById(Long teachDetailId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TeachDetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param teachDetail 实例对象
     * @return 对象列表
     */
    List<TeachDetail> queryAll(TeachDetail teachDetail);

    /**
     * 通过courseId批量查询
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<TeachDetail> mGetTeachDetailByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param teachDetail 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(TeachDetail teachDetail) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param teachDetailList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<TeachDetail> teachDetailList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param teachDetail 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(TeachDetail teachDetail) throws DataAccessException;

    /**
     * 批量修改数据
     *
     * @param updateTeachDetailDTOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateTeachDetailDTOs") List<UpdateTeachDetailDTO> updateTeachDetailDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param teachDetailId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long teachDetailId) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param teachDetailIds 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> teachDetailIds) throws DataAccessException;

    /**
     * 通过courseId批量删除数据
     *
     * @param courseId 课程id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;

}