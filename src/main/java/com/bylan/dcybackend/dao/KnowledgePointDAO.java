package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.bo.IloBO;
import com.bylan.dcybackend.entity.KnowledgePoint;
import com.bylan.dcybackend.vo.CourseProgressViewVO;
import com.bylan.dcybackend.vo.KnowlListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (KnowledgePoint)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface KnowledgePointDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param knowledgeId 主键
     * @return 实例对象
     */
    KnowledgePoint queryById(Long knowledgeId);

    /**
     * 通过iloId批量查询
     *
     * @param iloId IloId
     * @return 对象列表
     */
    List<KnowledgePoint> mGetKnowledgePointByIloId(Long iloId);

    /**
     * 通过知识点id批量查找其相关的iloId
     *
     * @param knowledgeIds 知识点id列表
     * @return iloId列表
     */
    List<Long> mGetIloIdByKnowledgeId(@Param("knowledgeIds") List<Long> knowledgeIds);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<KnowledgePoint> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param knowledgePoint 实例对象
     * @return 对象列表
     */
    List<KnowledgePoint> queryAll(KnowledgePoint knowledgePoint);

    /**
     * 通过iloId返回主键
     *
     * @param iloIds iloIds
     * @return 主键列表
     */
    List<Long> queryAllKeysByIloId(List<Long> iloIds);

    /**
     * 新增数据
     *
     * @param knowledgePoint 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(KnowledgePoint knowledgePoint) throws DataAccessException;

    /**
     * 批量新增数据
     *
     * @param knowledgePointList 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mInsert(List<KnowledgePoint> knowledgePointList) throws DataAccessException;

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
     * @param knowledgePoint 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(KnowledgePoint knowledgePoint) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param knowledgeId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long knowledgeId) throws DataAccessException;

    /**
     * 通过iloId批量删除数据
     *
     * @param iloIds iloIds
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int mDeleteByIloId(@Param("iloIds") List<Long> iloIds) throws DataAccessException;

    /**
     * 根据课程id批量获取知识点
     *
     * @param courseId 课程id
     * @return 知识点列表
     * @throws DataAccessException 数据库异常
     */
    List<KnowlListVO> mGetKnowledgeByCourseId(@Param("courseId") Long courseId) throws DataAccessException;

    /**
     * 根据课程id和周次获取课程进展
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 课程进展一览VO
     */
    CourseProgressViewVO mGetCourseProgressViewByCourseIdAndWeek(@Param("courseId") Long courseId, @Param("week") Long week);

    /**
     * 根据课程id和周次获取课程进展
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 课程进展一览VO
     */
    CourseProgressViewVO mGetAccumulatedCourseProgressViewByCourseIdAndWeek(@Param("courseId") Long courseId, @Param("week") Long week);

}