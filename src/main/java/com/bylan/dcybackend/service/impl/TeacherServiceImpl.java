package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.CourseDAO;
import com.bylan.dcybackend.dao.CurriculumDAO;
import com.bylan.dcybackend.dao.SectionTeacherDAO;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.entity.Course;
import com.bylan.dcybackend.entity.Curriculum;
import com.bylan.dcybackend.service.TeacherService;
import com.bylan.dcybackend.vo.CourseListVO;
import com.bylan.dcybackend.vo.CurriculumListVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 教师相关服务实现
 *
 * @author Rememorio
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger log = LogManager.getLogger(TeacherServiceImpl.class);

    @Autowired
    CurriculumDAO curriculumDao;

    @Autowired
    CourseDAO courseDAO;

    @Autowired
    SectionTeacherDAO sectionTeacherDAO;

    /**
     * 获取教师所教的课程id以及名称
     *
     * @param teacherId 教师
     * @return 课程VO
     */
    @Override
    public List<CurriculumListVO> getCurriculumListByTeacherId(String teacherId) {
        List<Curriculum> curricula = sectionTeacherDAO.queryDistinctCurriculumByTeacherId(teacherId);
        List<CurriculumListVO> curriculumLists = new ArrayList<>();
        for (Curriculum curriculum : curricula) {
            if (curriculum == null || curriculum.getCurriculumStatus().equals(Constant.Curriculum.CLOSED)) {
                continue;
            }
            CurriculumListVO curriculumListVO = new CurriculumListVO(curriculum);
            curriculumLists.add(curriculumListVO);
        }
        return curriculumLists;
    }

    /**
     * 获取教师所教的具体课程的信息
     *
     * @param teacherId    教师工号
     * @param curriculumId 课程id
     * @return 课程DTO
     */
    @Override
    public List<CourseListVO> getCourseListByTeacherIdAndCurriculumId(String teacherId, Long curriculumId) {
        List<Course> courses =
                sectionTeacherDAO.queryByTeacherIdAndCurriculumId(teacherId, curriculumId);
        List<CourseListVO> courseLists = new ArrayList<>();
        for (Course course : courses) {
            if (course == null || course.getCourseStatus().equals(Constant.Course.CLOSED)) {
                continue;
            }
            CourseListVO courseListVO = new CourseListVO(course);
            courseLists.add(courseListVO);
        }
        return courseLists;
    }

}
