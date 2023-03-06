package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.SectionStudent;
import com.bylan.dcybackend.entity.Student;
import com.bylan.dcybackend.vo.FriendMsgStatusVO;
import com.bylan.dcybackend.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SectionStudent)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface SectionStudentDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param sectionId 主键
     * @return 实例对象
     */
    SectionStudent queryById(Long sectionId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SectionStudent> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sectionStudent 实例对象
     * @return 对象列表
     */
    List<SectionStudent> queryAll(SectionStudent sectionStudent);

    /**
     * 新增数据
     *
     * @param sectionStudent 实例对象
     * @return 影响行数
     */
    int insert(SectionStudent sectionStudent);

    /**
     * 通过主键删除数据
     *
     * @param sectionId 主键
     * @return 影响行数
     */
    int deleteById(Long sectionId);

    /**
     * 获取学生id和名字
     *
     * @param sectionId 教学班id
     * @return 学生姓名和id
     */
    List<UserVO> getStuUserVoBySectionId(@Param("sectionId") Long sectionId);

    /**
     * 获取学生消息状态
     *
     * @param sectionId 教学班id
     * @param userId    用户id
     * @return 好友消息状态
     */
    List<FriendMsgStatusVO> getStuIdBySectionId(@Param("sectionId") Long sectionId,
                                                @Param("userId") String userId);


    /**
     * 获取同一个课程的学生的Id
     * @param courseId
     * @return
     */
    List<String> getStuIdByCourseId(@Param("courseId")Long courseId);
}