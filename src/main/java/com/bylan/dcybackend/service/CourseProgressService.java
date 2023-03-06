package com.bylan.dcybackend.service;

import com.bylan.dcybackend.vo.CourseProgressVO;
import com.bylan.dcybackend.vo.CourseProgressViewVO;

import java.util.List;

/**
 * 课程达成服务
 *
 * @author Rememorio
 * @date 2022-04-26 16:16
 */
public interface CourseProgressService {

    /**
     * 获取某一周的课程进展
     *
     * @param courseId 课程id
     * @param week     周次
     * @return 课程进展VO
     */
    List<CourseProgressVO> getCourseProgressByWeek(Long courseId, Long week);

    /**
     * 根据courseId获取当前最新周次
     *
     * @param courseId 课程id
     * @return 最新周次
     */
    Long getLatestWeekByCourseId(Long courseId);

    /**
     * 根据courseId获取课程进展一览
     *
     * @param courseId      课程id
     * @param week          周次
     * @param isAccumulated 是否累计到当前最新周
     * @return 课程进展一览VO
     */
    CourseProgressViewVO getCourseProgressViewByWeek(Long courseId, Long week, Boolean isAccumulated);

}
