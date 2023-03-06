package com.bylan.dcybackend.service;

import com.bylan.dcybackend.vo.StuCourseVO;

import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/28 20:31
 */
public interface StudentService {
    List<StuCourseVO> getStuCourseByStuId(String studentId);
}
