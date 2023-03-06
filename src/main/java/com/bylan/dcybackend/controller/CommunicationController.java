package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.service.CommunicationService;
import com.bylan.dcybackend.vo.DiscussionVO;
import com.bylan.dcybackend.vo.Feedback4StudentVO;
import com.bylan.dcybackend.vo.Feedback4TeacherVO;
import com.bylan.dcybackend.vo.NoticeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/21 10:01
 */
@Api(value = "学生交流相关", tags = {"学生交流"})
@RequestMapping("/comm")
@RestController
public class CommunicationController {

    private static final Logger log = LogManager.getLogger(SyllabusController.class);

    @Autowired
    CommunicationService communicationService;

    // ***************************评论区相关*************************************

    /**
     * 新增评论
     *
     * @param createDiscussionDTO 新增评论DTO
     * @return 评论
     */
    @PostMapping(value = "/addDiscussion")
    @ApiOperation("新增评论")
    @ResponseBody
    public CommonResult<Boolean> addDiscussion(@RequestBody @Valid CreateDiscussionDTO createDiscussionDTO) {
        log.info("——————————新增评论——————————");
        if (!createDiscussionDTO.isValid()) {
            return CommonResult.validateFailed();
        }
        Boolean result = communicationService.addDiscussion(createDiscussionDTO);
        return CommonResult.success(result);
    }


    @PostMapping(value = "/deleteDiscussion")
    @ApiOperation("删除评论")
    @ResponseBody
    public CommonResult<Boolean> deleteDiscussion(@RequestBody @Valid DeleteDiscussionDTO deleteDiscussionDTO) {
        log.info("——————————删除评论——————————");
        Boolean result = communicationService.deleteDiscussion(deleteDiscussionDTO);
        return CommonResult.success(result);
    }

    @GetMapping(value = "/getDiscussion")
    @ApiOperation("获取评论")
    @ResponseBody
    public CommonResult<List<DiscussionVO>> getDiscussion(@RequestParam("course_id")
                                                          @ApiParam("课程id")
                                                          @NotNull(message = "course_id不能为空")
                                                          @Min(value = 0, message = "course_id不能为负数")
                                                          Long courseId) {
        log.info("——————————获取评论——————————");
        List<DiscussionVO> list = communicationService.getDiscussionByCourseId(courseId);
        return CommonResult.success(list);
    }


    // ***************************公告相关*************************************

    @PostMapping(value = "/addNotice")
    @ApiOperation("新增公告")
    @ResponseBody
    public CommonResult<Boolean> addNotice(@RequestBody @Valid CreateNoticeDTO createNoticeDTO) {
        log.info("——————————新增评论——————————");
        Boolean result = communicationService.addNotice(createNoticeDTO);
        return CommonResult.success(result);
    }

    @PostMapping(value = "/deleteNotice")
    @ApiOperation("删除评论")
    @ResponseBody
    public CommonResult<Boolean> deleteNotice(@RequestBody @Valid DeleteNoticeDTO deleteNotice) {
        log.info("——————————删除评论——————————");
        Boolean result = communicationService.deleteNotice(deleteNotice);
        return CommonResult.success(result);
    }

    @GetMapping(value = "/getNotice")
    @ApiOperation("获取公告")
    @ResponseBody
    public CommonResult<List<NoticeVO>> getNotice(@RequestParam("section_id")
                                                  @ApiParam("教学班id")
                                                  @NotNull(message = "section_id不能为空")
                                                  @Min(value = 0, message = "section_id不能为负数")
                                                  Long sectionId) {
        log.info("——————————获取公告——————————");
        List<NoticeVO> list = communicationService.getNotice(sectionId);
        return CommonResult.success(list);
    }


    // ***************************反馈相关*************************************

    @PostMapping(value = "/addFeedback")
    @ApiOperation("新增反馈")
    @ResponseBody
    public CommonResult<Boolean> addFeedBack(@RequestBody @Valid CreateFeedbackDTO createFeedbackDTO) {
        log.info("——————————新增反馈——————————");
        createFeedbackDTO.pathTransfer();
        createFeedbackDTO.setFeedbackStatus(Constant.Feedback.CREATE);
        Boolean result = communicationService.addFeedback(createFeedbackDTO);
        return CommonResult.success(result);
    }

    @PostMapping(value = "/responseFeedback")
    @ApiOperation("老师回复反馈")
    @ResponseBody
    public CommonResult<Boolean> updateFeedback(@RequestBody @Valid UpdateFeedbackDTO updateFeedbackDTO) {
        log.info("——————————回复反馈——————————");
        updateFeedbackDTO.setFeedbackStatus(Constant.Feedback.REPLY);
        Boolean result = communicationService.updateFeedback(updateFeedbackDTO);
        return CommonResult.success(result);
    }

    @GetMapping(value = "/getFeedbackByTeacherId")
    @ApiOperation("获取反馈 教师端")
    @ResponseBody
    public CommonResult<List<Feedback4TeacherVO>> getFeedBack4Teacher(@RequestParam("teacher_id")
                                                                      @ApiParam("教师id")
                                                                      @NotBlank(message = "teacher_id不能为空")
                                                                      String teacherId) {
        log.info("——————————获取反馈——————————");
        List<Feedback4TeacherVO> list = communicationService.getFeedback4Teacher(teacherId);
        return CommonResult.success(list);
    }

    @GetMapping(value = "/getFeedbackByStudentId")
    @ApiOperation("获取反馈 学生端")
    @ResponseBody
    public CommonResult<List<Feedback4StudentVO>> getFeedBack4Student(@RequestParam("student_id")
                                                                      @ApiParam("学生id")
                                                                      @NotBlank(message = "student_id不能为空")
                                                                      String studentId) {
        log.info("——————————获取反馈——————————");
        List<Feedback4StudentVO> list = communicationService.getFeedback4Student(studentId);
        return CommonResult.success(list);
    }

}
