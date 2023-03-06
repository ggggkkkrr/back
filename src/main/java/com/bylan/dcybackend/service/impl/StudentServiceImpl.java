package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.SectionDAO;
import com.bylan.dcybackend.service.StudentService;
import com.bylan.dcybackend.vo.StuCourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/28 20:32
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private SectionDAO sectionDAO;

    @Override
    public List<StuCourseVO> getStuCourseByStuId(String studentId) {
        return sectionDAO.getStuCourseByStudentId(studentId);
    }
}
