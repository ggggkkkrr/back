package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.Course;
import com.bylan.dcybackend.entity.Curriculum;
import com.bylan.dcybackend.entity.SectionTeacher;
import com.bylan.dcybackend.vo.FriendMsgStatusVO;
import com.bylan.dcybackend.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (SectionTeacher)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-14 16:05:25
 */
public interface SectionTeacherDAO {

    /**
     * 获取教学班-教师
     *
     * @param teacherId 教师工号
     * @return 教学班-教师
     */
    List<Curriculum> queryDistinctCurriculumByTeacherId(String teacherId);

    /**
     * 获取教学班-教师
     *
     * @param teacherId    教师工号
     * @param curriculumId 课程id
     * @return 教学班-教师
     */
    List<Course> queryByTeacherIdAndCurriculumId(String teacherId, Long curriculumId);

    /**
     * 通过教师工号查询在授课程id
     *
     * @param teacherId 教师工号
     * @return 课程id列表
     */
    List<Long> getOpenedCourseIdByTeacherId(String teacherId);

    /**
     * 通过ID查询单条数据
     *
     * @param sectionId 主键
     * @param teacherId 主键
     * @return 实例对象
     */
    SectionTeacher queryById(Long sectionId, Long teacherId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SectionTeacher> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sectionTeacher 实例对象
     * @return 对象列表
     */
    List<SectionTeacher> queryAll(SectionTeacher sectionTeacher);

    /**
     * 新增数据
     *
     * @param sectionTeacher 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int insert(SectionTeacher sectionTeacher) throws DataAccessException;

    /**
     * 修改数据
     *
     * @param sectionTeacher 实例对象
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int update(SectionTeacher sectionTeacher) throws DataAccessException;

    /**
     * 通过主键删除数据
     *
     * @param sectionId 主键
     * @param teacherId 主键
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteById(Long sectionId, Long teacherId) throws DataAccessException;

    /**
     * 获取教师姓名和id
     *
     * @param sectionId 教学班id
     * @return 教师姓名和id
     */
    List<UserVO> getTeaUserVoBySectionId(@Param("sectionId") Long sectionId);

    /**
     * 获取教师消息状态
     *
     * @param sectionId 教学班id
     * @param userId    用户id
     * @return 好友消息状态
     */
    List<FriendMsgStatusVO> getTeaIdBySectionId(@Param("sectionId") Long sectionId,
                                                @Param("userId") String userId);

    /**
     * 通过courseId获取教师Id
     * @param courseId
     * @return
     */
    List<String> getTeaIdByCourseId(Long courseId);
}