package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.UpdatePracticeDTO;
import com.bylan.dcybackend.entity.Practice;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Practice)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface PracticeDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param practiceId 主键
     * @return 实例对象
     */
    Practice queryById(Long practiceId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Practice> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param practice 实例对象
     * @return 对象列表
     */
    List<Practice> queryAll(Practice practice);

    /**
     * 通过courseId批量查询
     *
     * @param courseId 课程id
     * @return 对象列表
     */
    List<Practice> mGetPracticeByCourseId(Long courseId);

    /**
     * 新增数据
     *
     * @param practice 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(Practice practice) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param practiceList 实例对象列表
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<Practice> practiceList) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param practice 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(Practice practice) throws DataAccessException;

    /**
     * 修批量改数据
     *
     * @param updatePracticeDTOList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mUpdate(@Param("updatePracticeDTOs") List<UpdatePracticeDTO> updatePracticeDTOList) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param practiceId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long practiceId) throws DataAccessException;

    /**
     * 通过主键批量删除数据
     *
     * @param practiceIdList 主键s
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteById(List<Long> practiceIdList) throws DataAccessException;

    /**
     * 通过courseId批量删除数据
     *
     * @param courseId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByCourseId(Long courseId) throws DataAccessException;

}