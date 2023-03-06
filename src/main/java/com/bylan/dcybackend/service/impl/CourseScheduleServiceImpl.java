package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.CourseScheduleDAO;
import com.bylan.dcybackend.dto.CreateCourseScheduleDTO;
import com.bylan.dcybackend.dto.UpdateCourseScheduleDTO;
import com.bylan.dcybackend.entity.CourseSchedule;
import com.bylan.dcybackend.service.CourseScheduleService;
import com.bylan.dcybackend.vo.CourseScheduleVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 教学大纲的学习进度服务实现
 *
 * @author Rememorio
 */
@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

    private static final Logger log = LogManager.getLogger(CourseScheduleServiceImpl.class);

    @Autowired
    CourseScheduleDAO courseScheduleDAO;

    /**
     * 通过课程id获取学习进度
     *
     * @param courseId 课程id
     * @return 学习进度列表
     */
    @Override
    public List<CourseScheduleVO> getCourseSchedule(Long courseId) {
        List<CourseSchedule> courseScheduleList = courseScheduleDAO.mGetCourseScheduleByCourseId(courseId);
        List<CourseScheduleVO> courseScheduleVOList = new ArrayList<>(courseScheduleList.size());
        for (CourseSchedule courseSchedule : courseScheduleList) {
            CourseScheduleVO courseScheduleVO = new CourseScheduleVO(courseSchedule);
            courseScheduleVOList.add(courseScheduleVO);
        }
        return courseScheduleVOList;
    }

    /**
     * 批量删除
     *
     * @param courseScheduleIds 学习进度列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeleteCourseSchedule(List<Long> courseScheduleIds) {
        try {
            courseScheduleDAO.mDeleteById(courseScheduleIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除course_schedule时失败 堆栈信息Ids: {}; 数据信息:{}", e, courseScheduleIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateCourseScheduleDTOList 学习进度列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdateCourseSchedule(List<UpdateCourseScheduleDTO> updateCourseScheduleDTOList) {
        try {
            courseScheduleDAO.mUpdate(updateCourseScheduleDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新course_schedule时失败 堆栈信息: {}; 数据信息: {}", e, updateCourseScheduleDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createCourseScheduleDTO 新增学习进度DTO
     * @return 成功
     */
    @Override
    public Boolean insertCourseSchedule(CreateCourseScheduleDTO createCourseScheduleDTO) {
        try {
            CourseSchedule courseSchedule = new CourseSchedule(createCourseScheduleDTO);
            courseScheduleDAO.insert(courseSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入course_schedule时失败 堆栈信息: {}; 数据信息: {}", e, createCourseScheduleDTO);
            return false;
        }
        return true;
    }
}
