package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.DeleteIloAssessStructDTO;
import com.bylan.dcybackend.dto.UpdateIloAssessStructDTO;
import com.bylan.dcybackend.entity.IloAssessStruct;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (IloAssessStruct)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface IloAssessStructDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param iloId          主键
     * @param assessStructId 主键
     * @return 实例对象
     */
    IloAssessStruct queryById(Long iloId, Long assessStructId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<IloAssessStruct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param iloAssessStruct 实例对象
     * @return 对象列表
     */
    List<IloAssessStruct> queryAll(IloAssessStruct iloAssessStruct);

    /**
     * 通过assessStructId批量查询
     *
     * @param assessStructId 课程考核结构id
     * @return 对象列表
     */
    List<IloAssessStruct> mGetIloAssessStructByAssessStructId(Long assessStructId);

    /**
     * 通过assessStructIds批量查询
     *
     * @param assessStructIds 课程考核结构id
     * @return 对象列表
     */
    List<IloAssessStruct> mGetIloAssessStructByAssessStructIds(List<Long> assessStructIds);

    /**
     * 新增数据
     *
     * @param iloAssessStruct 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(IloAssessStruct iloAssessStruct) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param iloAssessStructList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<IloAssessStruct> iloAssessStructList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param iloAssessStruct 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(IloAssessStruct iloAssessStruct) throws DataAccessException;

    /**
     * 批量修改数据
     *
     * @param updateIloAssessStructDTOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateIloAssessStructDTOs") List<UpdateIloAssessStructDTO> updateIloAssessStructDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param iloId          主键
     * @param assessStructId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long iloId, Long assessStructId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param deleteIloAssessStructDTOList 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(@Param("deleteIloAssessStructDTOs") List<DeleteIloAssessStructDTO> deleteIloAssessStructDTOList) throws DataAccessException;

    /**
     * 通过iloId或者assessStructId批量删除数据
     *
     * @param iloIds          主键
     * @param assessStructIds 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByIloIdOrAssessStructId(@Param("iloIds") List<Long> iloIds, @Param("assessStructIds") List<Long> assessStructIds) throws DataAccessException;

}