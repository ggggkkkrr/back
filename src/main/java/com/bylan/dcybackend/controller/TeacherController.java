package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.service.TeacherService;
import com.bylan.dcybackend.vo.CourseListVO;
import com.bylan.dcybackend.vo.CurriculumListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 教师相关
 *
 * @author Rememorio
 */
@Api(value = "教师相关", tags = {"教师"})
@RequestMapping("teacher")
@RestController
public class TeacherController {

    private static final Logger log = LogManager.getLogger(TeacherController.class);

    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = "/getCurriculumList")
    @ApiOperation(value = "获取教师所教的课程id以及名称", notes = "传入教师工号")
    @ResponseBody
    public CommonResult<List<CurriculumListVO>> getCurriculumList(@RequestParam("teacher_id")
                                                                  @ApiParam("教师工号")
                                                                  @NotBlank(message = "教师工号不能为空")
                                                                  String teacherId) {
        log.info("——————————获取教师所教的课程id以及名称——————————");
        log.info("teacherId: {}", teacherId);
        List<CurriculumListVO> curriculumLists = teacherService.getCurriculumListByTeacherId(teacherId);
        log.info("课程列表: {}", curriculumLists);
        return CommonResult.success(curriculumLists);
    }

    @GetMapping(value = "/getCourseList")
    @ApiOperation(value = "获取教师所教的具体课程的信息", notes = "传入教师工号和课程id")
    @ResponseBody
    public CommonResult<List<CourseListVO>> getCourseList(@RequestParam("teacher_id")
                                                          @ApiParam("教师工号")
                                                          @NotBlank(message = "教师工号不能为空")
                                                          String teacherId,
                                                          @RequestParam("curriculum_id")
                                                          @ApiParam("课程id")
                                                          @NotNull(message = "课程id不能为空")
                                                          @Min(value = 0, message = "课程id不能为负数")
                                                          Long curriculumId) {
        log.info("——————————获取教师所教的具体课程的信息——————————");
        log.info("teacherId: {}, curriculumId: {}", teacherId, curriculumId);
        List<CourseListVO> courseLists = teacherService.getCourseListByTeacherIdAndCurriculumId(teacherId, curriculumId);
        log.info("课程列表: {}", courseLists);
        return CommonResult.success(courseLists);
    }


}
