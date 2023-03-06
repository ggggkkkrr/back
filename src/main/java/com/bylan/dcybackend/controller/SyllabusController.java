package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.UpdateCourseDTO;
import com.bylan.dcybackend.service.SyllabusService;
import com.bylan.dcybackend.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;

/**
 * 教学大纲
 *
 * @author Rememorio
 */
@Api(value = "教学大纲相关", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class SyllabusController {

    private static final Logger log = LogManager.getLogger(SyllabusController.class);

    @Autowired
    SyllabusService syllabusService;

    @GetMapping(value = "/getCourse")
    @ApiOperation("获取课程基本信息")
    @ResponseBody
    public CommonResult<CourseVO> getCourse(@RequestParam("course_id")
                                            @ApiParam("课程id")
                                            @NotNull(message = "course_id不能为空")
                                            @PositiveOrZero(message = "course_id不能为负数")
                                            Long courseId) {
        log.info("——————————获取课程基本信息——————————");
        log.info("courseId: {}", courseId);
        CourseVO courseVO = syllabusService.getCourseByCourseId(courseId);
        log.info("return: {}", courseVO);
        return CommonResult.success(courseVO);
    }

    @PostMapping(value = "/updateCourse")
    @ApiOperation("更新课程基本信息")
    @ResponseBody
    public CommonResult<Boolean> updateCourse(@RequestBody @Valid UpdateCourseDTO updateCourseDTO) {
        log.info("——————————更新课程基本信息——————————");
        log.info("updateCourseDTO: {}", updateCourseDTO);
        if (!updateCourseDTO.isValid()) {
            log.warn("参数验证失败");
            return CommonResult.validateFailed();
        }
        Boolean result = syllabusService.updateCourse(updateCourseDTO);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @GetMapping(value = "/downloadTemplate")
    @ApiOperation("下载大纲模版")
    @ResponseBody
    public void downloadTemplate(HttpServletResponse response) {
        log.info("——————————下载大纲模版——————————");
        syllabusService.downloadTemplate(response);
    }

    @PostMapping(value = "/upload")
    @ApiOperation("上传教学大纲")
    @ResponseBody
    public CommonResult<String> downloadSyllabus(@RequestParam("file")
                                                 @ApiParam("文件")
                                                 @NotNull(message = "文件不能为空")
                                                 MultipartFile file,
                                                 @RequestParam("course_id")
                                                 @ApiParam("课程id")
                                                 @NotNull(message = "课程id不能为空")
                                                 @PositiveOrZero(message = "course_id不能为负数")
                                                 Long courseId) {
        log.info("——————————上传教学大纲——————————");
        String filename;
        try {
            filename = syllabusService.uploadSyllabus(file);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("文件名不合法: {}", e.getMessage());
            return CommonResult.validateFailed("文件名不合法");
        }
        log.info("文件名: {}", filename);
        try {
            syllabusService.parseSyllabus(file, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析失败，教学大纲不合法: {}", e.getMessage());
            // 删除已经上传的文件
            Boolean result = syllabusService.deleteSyllabusFile(filename);
            log.info("删除已上传的文件{}状态: {}", filename, result);
            return CommonResult.failed("教学大纲不合法");
        }
        return CommonResult.success(filename);
    }

    @GetMapping(value = "/download")
    @ApiOperation("下载教学大纲")
    @ResponseBody
    public void downloadSyllabus(@RequestParam("filename")
                                 @ApiParam("文件名")
                                 @NotBlank(message = "文件名不能为空")
                                 String filename, HttpServletResponse response) {
        log.info("——————————下载教学大纲——————————");
        log.info("filename: {}", filename);
        syllabusService.downloadSyllabus(filename, response);
    }

    @GetMapping(value = "/generate")
    @ApiOperation("生成教学大纲")
    @ResponseBody
    public void generateSyllabus(@RequestParam("course_id")
                                 @ApiParam("课程id")
                                 @NotNull(message = "课程id不能为空")
                                 @PositiveOrZero(message = "course_id不能为负数")
                                 Long courseId, HttpServletResponse response) {
        log.info("——————————生成教学大纲——————————");
        log.info("courseId: {}", courseId);
        String filename = syllabusService.generateSyllabus(courseId);
        log.info("filename: {}", filename);
        syllabusService.downloadSyllabus(filename, response);
    }

    @GetMapping(value = "/view")
    @ApiOperation("查看教学大纲")
    @ResponseBody
    public void viewSyllabus(@RequestParam("course_id")
                                 @ApiParam("课程id")
                                 @NotNull(message = "课程id不能为空")
                                 @PositiveOrZero(message = "course_id不能为负数")
                                         Long courseId, HttpServletResponse response) throws IOException {
        log.info("——————————查看教学大纲——————————");
        log.info("courseId: {}", courseId);
        String filename = syllabusService.generateSyllabus(courseId);
        String newFileName = syllabusService.viewSyllabus(filename, courseId);
        log.info("filename: {}", newFileName);
        syllabusService.downloadSyllabus(newFileName, response);
    }
}
