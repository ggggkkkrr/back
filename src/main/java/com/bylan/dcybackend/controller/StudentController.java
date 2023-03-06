package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.service.StudentService;
import com.bylan.dcybackend.vo.StuCourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/28 20:10
 */
@Api(value = "学生相关", tags = {"学生"})
@RequestMapping("student")
@RestController
public class StudentController {
    private static final Logger log = LogManager.getLogger(StudentController.class);

    @Autowired

    private StudentService studentSerivce;

    @GetMapping(value = "/getCourseList")
    @ApiOperation(value = "获取学生所有的具体课程的信息", notes = "传入学生学号")
    @ResponseBody
    public CommonResult<List<StuCourseVO>> getCourseList(
            @RequestParam("student_id") @ApiParam("学生学号") @NotBlank(message = "学生学号不能为空") String studentId
    ) {
        log.info("——————————获取学生所有课程——————————");
        log.info("studentId: {}", studentId);
        List<StuCourseVO> courseLists = studentSerivce.getStuCourseByStuId(studentId);
        log.info("课程列表: {}", courseLists);
        return CommonResult.success(courseLists);
    }
}
