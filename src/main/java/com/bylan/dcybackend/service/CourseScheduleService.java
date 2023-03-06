package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.CreateCourseScheduleDTO;
import com.bylan.dcybackend.dto.UpdateCourseScheduleDTO;
import com.bylan.dcybackend.vo.CourseScheduleVO;

import java.util.List;

/**
 * 教学大纲的学习进度服务
 *
 * @author Rememorio
 */
public interface CourseScheduleService {

    /**
     * 通过课程id获取学习进度
     *
     * @param courseId 课程id
     * @return 学习进度列表
     */
    List<CourseScheduleVO> getCourseSchedule(Long courseId);

    /**
     * 批量删除
     *
     * @param courseScheduleIds 学习进度id列表
     * @return 成功
     */
    Boolean mDeleteCourseSchedule(List<Long> courseScheduleIds);


    /**
     * 批量更新
     *
     * @param updateCourseScheduleDTOList 更新学习进度DTO
     * @return 成功
     */
    Boolean mUpdateCourseSchedule(List<UpdateCourseScheduleDTO> updateCourseScheduleDTOList);

    /**
     * 插入单个
     *
     * @param createCourseScheduleDTO 新增学习进度DTO
     * @return 成功
     */
    Boolean insertCourseSchedule(CreateCourseScheduleDTO createCourseScheduleDTO);

}
