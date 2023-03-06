package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.service.CourseProgressService;
import com.bylan.dcybackend.service.ReachService;
import com.bylan.dcybackend.vo.CourseProgressVO;
import com.bylan.dcybackend.vo.CourseProgressViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 课程达成
 *
 * @author Rememorio
 * @date 2022-05-03 12:50
 */
@Api(value = "课程达成相关", tags = {"课程达成"})
@RequestMapping("/courseProgress")
@RestController
public class CourseProgressController {

    private static final Logger log = LogManager.getLogger(CourseProgressController.class);

    @Autowired
    CourseProgressService courseProgressService;

    @Autowired
    ReachService reachService;

    @GetMapping(value = "/getCourseProgress")
    @ApiOperation("获取课程达成")
    @ResponseBody
    public CommonResult<List<CourseProgressVO>> getCourseProgress(@RequestParam("course_id")
                                                                  @ApiParam("课程id")
                                                                  @PositiveOrZero(message = "课程id不能为负数")
                                                                  Long courseId,
                                                                  @RequestParam("week")
                                                                  @ApiParam("周次")
                                                                  @PositiveOrZero(message = "周次不能为负数")
                                                                  Long week) {
        log.info("——————————获取课程达成——————————");
        List<CourseProgressVO> courseProgressVOList = courseProgressService.getCourseProgressByWeek(courseId, week);
        log.info("课程达成: {}", courseProgressVOList);
        return CommonResult.success(courseProgressVOList);
    }

    @GetMapping(value = "/getLatestWeek")
    @ApiOperation("获取最新周次")
    @ResponseBody
    public CommonResult<Long> getLatestWeek(@RequestParam("course_id")
                                            @ApiParam("课程id")
                                            @PositiveOrZero(message = "课程id不能为负数")
                                            Long courseId) {
        log.info("——————————获取最新周次——————————");
        Long week = courseProgressService.getLatestWeekByCourseId(courseId);
        log.info("最新周次: {}", week);
        if (week == null) {
            return CommonResult.notFound();
        }
        return CommonResult.success(week);
    }

    @GetMapping(value = "/getCourseProgressView")
    @ApiOperation("获取课程达成一览")
    @ResponseBody
    public CommonResult<CourseProgressViewVO> getCourseProgressView(@RequestParam("course_id")
                                                                    @ApiParam("课程id")
                                                                    @PositiveOrZero(message = "课程id不能为负数")
                                                                    Long courseId,
                                                                    @RequestParam("week")
                                                                    @ApiParam("周次")
                                                                    @PositiveOrZero(message = "周次不能为负数")
                                                                    Long week,
                                                                    @RequestParam("is_accumulated")
                                                                    @ApiParam("是否累计到当前最新周")
                                                                    @NotNull(message = "是否累计到当前最新周不能为空")
                                                                    Boolean isAccumulated) {
        log.info("——————————获取课程达成——————————");
        // 获取不带达成度的课程进展
        CourseProgressViewVO courseProgressViewVO = courseProgressService.getCourseProgressViewByWeek(courseId, week, isAccumulated);
        // 填充达成度
        reachService.fillCourseReach(courseProgressViewVO, courseId, week);
        log.info("课程达成一览: {}", courseProgressViewVO);
        return CommonResult.success(courseProgressViewVO);
    }

}
