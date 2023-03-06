package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.component.ReachCalculateTask;
import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.service.ReachService;
import com.bylan.dcybackend.vo.HomeCourseReachVO;
import com.bylan.dcybackend.vo.HomeStuReachVO;
import com.bylan.dcybackend.vo.StuReachDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 达成度
 *
 * @author Rememorio
 * @date 2022/3/23 21:57
 */
@Api(value = "达成度", tags = {"达成度"})
@RequestMapping("/reach")
@RestController
public class ReachController {
    private static final Logger log = LogManager.getLogger(SyllabusController.class);

    @Autowired
    private ReachService reachService;

    @GetMapping(value = "/getCourseReach")
    @ApiOperation("获取教师所教的所有课程的达成度")
    @ResponseBody
    public CommonResult<List<HomeCourseReachVO>> getCourseReachList(@RequestParam("teacher_id")
                                                                    @ApiParam("教师工号")
                                                                    @NotBlank(message = "教师工号不能为空")
                                                                    String teacherId) {
        log.info("——————————获取教师所教的所有课程的达成度——————————");
        // 先从缓存读取
        List<HomeCourseReachVO> courseReachVOList = ReachCalculateTask.getHomeReach(teacherId);
        if (courseReachVOList != null) {
            log.info("从缓存读取课程达成度：{}", courseReachVOList);
            return CommonResult.success(courseReachVOList);
        }
        // 没有再计算
        List<Long> courseIds = reachService.mGetCourseIdByTeacherId(teacherId);
        courseReachVOList = reachService.mGetCourseReachByCourseId(courseIds);
        boolean result = ReachCalculateTask.setHomeReach(teacherId, courseReachVOList);
        log.info("重新设置课程达成度：{}，状态：{}", courseReachVOList, result);
        return CommonResult.success(courseReachVOList);
    }

    @GetMapping(value = "/getStuReach")
    @ApiOperation("获取教学班的所有学生的达成度")
    @ResponseBody
    public CommonResult<List<HomeStuReachVO>> getStuReach(@RequestParam("section_id")
                                                          @ApiParam("教学班id")
                                                          @NotNull(message = "教学班id不能为空")
                                                          Long sectionId,
                                                          @RequestParam(value = "week", required = false)
                                                          @ApiParam("教学周")
                                                          Long week,
                                                          @RequestParam(value = "assert_type", required = false)
                                                          @ApiParam("考核类型")
                                                          Long assertType) {
        log.info("——————————获取教学班的所有学生的达成度——————————");
        List<HomeStuReachVO> homeStuReachVOList;
        try {
            homeStuReachVOList = reachService.mGetStuReachBySectionIdAndWeek(sectionId, week);
        } catch (Exception e) {
            log.info("知识点结构缺失：{}", e.getMessage());
            return CommonResult.failed("知识点结构缺失");
        }
        return CommonResult.success(homeStuReachVOList);

    }

    @GetMapping(value = "/getPersonReach")
    @ApiOperation("获取教学班的所有学生的达成度")
    @ResponseBody
    public CommonResult<Double> getPersonReach(@RequestParam("section_id")
                                               @ApiParam("教学班id")
                                               @NotNull(message = "教学班id不能为空")
                                               Long sectionId,
                                               @RequestParam("student_id")
                                               @ApiParam("学生id")
                                               @NotNull(message = "学生id不能为空")
                                               String studentId,
                                               @RequestParam(value = "week", required = false)
                                               @ApiParam("教学周")
                                               Long week,
                                               @RequestParam(value = "assert_type", required = false)
                                               @ApiParam("考核类型")
                                               Long assertType) {
        log.info("——————————获取教学班的所有学生的达成度——————————");
        Double actualScore;
        try {
            actualScore = reachService.getStuReachByStudentIdAndWeekAndAssertTypeAndSectionId(studentId, week, assertType, sectionId);
        } catch (Exception e) {
            log.info("知识点结构缺失：{}", e.getMessage());
            return CommonResult.failed("知识点结构缺失");
        }
        return CommonResult.success(actualScore);

    }

    @GetMapping(value = "/getStuReachDetail")
    @ApiOperation("获取教学班的所有学生教学周分数细则")
    @ResponseBody
    public CommonResult<StuReachDetailVO> getStuReachDetail(@RequestParam("section_id")
                                                            @ApiParam("教学班id")
                                                            @NotNull(message = "教学班id不能为空")
                                                            Long sectionId,
                                                            @RequestParam(value = "week")
                                                            @ApiParam("教学周")
                                                            Long week,
                                                            @RequestParam(value = "assert_type")
                                                            @ApiParam("考核类型")
                                                            Long assertType) {
        log.info("——————————获取教学班的所有学生教学周分数细则——————————");
        StuReachDetailVO stuReachDetailVO;
        try {
            stuReachDetailVO = reachService.mGetStuReachDetailBySectionIdAndWeek(sectionId, week, assertType);
        } catch (Exception e) {
            log.info("知识点结构缺失：{}", e.getMessage());
            return CommonResult.failed("知识点结构缺失");
        }
        return CommonResult.success(stuReachDetailVO);
    }

    @GetMapping(value = "/getLatestWeek")
    @ApiOperation("获取最新周次")
    @ResponseBody
    public CommonResult<Long> getLatestWeek(@RequestParam("section_id")
                                            @ApiParam("教学班id")
                                            @PositiveOrZero(message = "教学班id不能为负数")
                                            Long sectionId,
                                            @RequestParam(value = "assess_type", required = false)
                                            @ApiParam("考核类型")
                                            Long assessType) {
        log.info("——————————获取最新周次——————————");
        Long week = reachService.getLatestWeekBySectionId(sectionId, assessType);
        log.info("最新周次: {}", week);
        if (week == null) {
            return CommonResult.notFound();
        }
        return CommonResult.success(week);
    }

}
