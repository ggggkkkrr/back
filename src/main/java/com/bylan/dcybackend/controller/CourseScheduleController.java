package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CreateCourseScheduleDTO;
import com.bylan.dcybackend.dto.DeleteCourseScheduleDTO;
import com.bylan.dcybackend.dto.UpdateCourseScheduleDTO;
import com.bylan.dcybackend.service.CourseScheduleService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.CourseScheduleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 教学大纲的学习进度
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的学习进度", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class CourseScheduleController {

    private static final Logger log = LogManager.getLogger(CourseScheduleController.class);

    @Autowired
    CourseScheduleService courseScheduleService;

    @GetMapping(value = "/getCourseSchedule")
    @ApiOperation("获取学习进度列表")
    @ResponseBody
    public CommonResult<List<CourseScheduleVO>> getCourseSchedule(@RequestParam("course_id")
                                                                  @ApiParam("课程id")
                                                                  @NotNull(message = "course_id不能为空")
                                                                  @PositiveOrZero(message = "course_id不能为负数")
                                                                  Long courseId) {
        log.info("——————————获取学习进度列表——————————");
        log.info("courseId: {}", courseId);
        List<CourseScheduleVO> courseScheduleVOList = courseScheduleService.getCourseSchedule(courseId);
        log.info("return: {}", courseScheduleVOList);
        return CommonResult.success(courseScheduleVOList);
    }

    @PostMapping(value = "/updateCourseSchedule")
    @ApiOperation("批量更新学习进度")
    @ResponseBody
    public CommonResult<Boolean> mUpdateCourseSchedule(@RequestBody @Valid ValidList<UpdateCourseScheduleDTO> updateCourseScheduleDTOList) {
        log.info("——————————批量更新学习进度——————————");
        for (UpdateCourseScheduleDTO each : updateCourseScheduleDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = courseScheduleService.mUpdateCourseSchedule(updateCourseScheduleDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteCourseSchedule")
    @ApiOperation("批量删除学习进度")
    @ResponseBody
    public CommonResult<Boolean> mDeleteCourseSchedule(@RequestBody @Valid DeleteCourseScheduleDTO deleteCourseScheduleDTO) {
        log.info("———————————批量删除学习进度—————————————");
        Boolean result = courseScheduleService.mDeleteCourseSchedule(deleteCourseScheduleDTO.getScheduleIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertCourseSchedule")
    @ApiOperation("新增学习进度")
    @ResponseBody
    public CommonResult<Boolean> insertCourseSchedule(@RequestBody @Valid CreateCourseScheduleDTO createCourseScheduleDTO) {
        log.info("——————————新增学习进度——————————");
        Boolean result = courseScheduleService.insertCourseSchedule(createCourseScheduleDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

}

