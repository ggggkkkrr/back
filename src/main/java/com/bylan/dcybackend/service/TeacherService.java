package com.bylan.dcybackend.service;

import com.bylan.dcybackend.vo.CourseListVO;
import com.bylan.dcybackend.vo.CurriculumListVO;

import java.util.List;

/**
 * 教师相关服务
 *
 * @author Rememorio
 */
public interface TeacherService {
    /**
     * 获取教师所教的课程id以及名称
     *
     * @param teacherId 教师工号
     * @return 课程DTO
     */
    List<CurriculumListVO> getCurriculumListByTeacherId(String teacherId);

    /**
     * 获取教师所教的具体课程的信息
     *
     * @param teacherId    教师工号
     * @param curriculumId 课程id
     * @return 课程DTO
     */
    List<CourseListVO> getCourseListByTeacherIdAndCurriculumId(String teacherId, Long curriculumId);

}
