package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.bo.IloBO;
import com.bylan.dcybackend.dto.UpdateIloDTO;
import com.bylan.dcybackend.entity.Ilo;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Ilo)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface IloDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param iloId 主键
     * @return 实例对象
     */
    Ilo queryById(Long iloId);

    /**
     * 通过courseId批量查询数据
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<Ilo> mGetIloByCourseId(Long courseId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Ilo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param ilo 实例对象
     * @return 对象列表
     */
    List<Ilo> queryAll(Ilo ilo);

    /**
     * 通过courseId返回主键
     *
     * @param courseId 课程id
     * @return 主键列表
     */
    List<Long> queryAllByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param ilo 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(Ilo ilo) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param iloList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<Ilo> iloList) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param iloBOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsertByIloBo(List<IloBO> iloBOList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param ilo 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(Ilo ilo) throws DataAccessException;

    /**
     * 批量修改数据
     *
     * @param updateIloDTOList 对象列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateIloDTOs") List<UpdateIloDTO> updateIloDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param iloId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long iloId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param iloIds 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> iloIds) throws DataAccessException;

    /**
     * 通过courseId批量删除数据
     *
     * @param courseId 课程id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;

}