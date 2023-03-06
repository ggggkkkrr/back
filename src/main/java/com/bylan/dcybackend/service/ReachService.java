package com.bylan.dcybackend.service;

import com.bylan.dcybackend.vo.CourseProgressViewVO;
import com.bylan.dcybackend.vo.HomeCourseReachVO;
import com.bylan.dcybackend.vo.HomeStuReachVO;
import com.bylan.dcybackend.vo.StuReachDetailVO;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * 达成度服务
 *
 * @author Rememorio
 */
public interface ReachService {

    /**
     * 通过教师工号获取在授课程
     *
     * @param teacherId 教师工号
     * @return 课程ids
     */
    List<Long> mGetCourseIdByTeacherId(String teacherId);

    /**
     * 获取最新教学周的课程达成度
     *
     * @param courseIds 课程ids
     * @return 首页课程达成度VO
     */
    List<HomeCourseReachVO> mGetCourseReachByCourseId(List<Long> courseIds);

    /**
     * 填充课程进展的达成度
     *
     * @param courseProgressViewVO 课程进展
     * @param courseId             课程id
     * @param week                 周次
     */
    void fillCourseReach(CourseProgressViewVO courseProgressViewVO, Long courseId, Long week);

    /**
     * 获取学生个人达成度
     *
     * @param studentId
     * @param week
     * @param assertType
     * @param sectionId
     * @return
     * @throws InvalidPropertiesFormatException
     */
    Double getStuReachByStudentIdAndWeekAndAssertTypeAndSectionId(String studentId, Long week, Long assertType, Long sectionId) throws InvalidPropertiesFormatException;

    /**
     * 获取教学班所有同学的达成度
     *
     * @param sectionId
     * @param week
     * @return
     */
    List<HomeStuReachVO> mGetStuReachBySectionIdAndWeek(Long sectionId, Long week) throws InvalidPropertiesFormatException;


    /**
     * 获取教学班所有同学教学周题目和学生得分细则
     *
     * @param sectionId
     * @param week
     * @param assertType
     * @return
     */
    StuReachDetailVO mGetStuReachDetailBySectionIdAndWeek(Long sectionId, Long week, Long assertType);

    /**
     * 根据教学班获取教学周
     *
     * @param sectionId
     * @param assessType
     * @return
     */
    Long getLatestWeekBySectionId(Long sectionId, Long assessType);
}
