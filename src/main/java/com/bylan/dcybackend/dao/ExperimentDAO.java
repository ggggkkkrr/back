package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.UpdateExperimentDTO;
import com.bylan.dcybackend.entity.Experiment;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Experiment)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface ExperimentDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param experimentId 主键
     * @return 实例对象
     */
    Experiment queryById(Long experimentId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Experiment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param experiment 实例对象
     * @return 对象列表
     */
    List<Experiment> queryAll(Experiment experiment);

    /**
     * 通过courseId批量查询
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<Experiment> mGetExperimentByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param experiment 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(Experiment experiment) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param experimentList 实例对象列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<Experiment> experimentList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param experiment 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(Experiment experiment) throws DataAccessException;

    /**
     * 批量修改数据
     *
     * @param updateExperimentDTOList 对象列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updateExperimentDTOs") List<UpdateExperimentDTO> updateExperimentDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param experimentId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long experimentId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param experimentIdList 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> experimentIdList) throws DataAccessException;

    /**
     * 通过courseId批量删除数据
     *
     * @param courseId 课程id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;

}