package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.service.AssessService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.AssessStructVO;
import com.bylan.dcybackend.vo.IloAssessStructVO;
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
 * 教学大纲的课程考核
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的课程考核结构", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class AssessController {

    private static final Logger log = LogManager.getLogger(AssessController.class);

    @Autowired
    AssessService assessService;

    @GetMapping(value = "/getAssessStruct")
    @ApiOperation("获取教学课程考核结构")
    @ResponseBody
    public CommonResult<List<AssessStructVO>> getAssessStruct(@RequestParam("course_id")
                                                              @ApiParam("课程id")
                                                              @NotNull(message = "course_id不能为空")
                                                              @PositiveOrZero(message = "course_id不能为负数")
                                                              Long courseId) {
        log.info("——————————获取课程考核结构列表——————————");
        log.info("courseId: {}", courseId);
        List<AssessStructVO> assessStructVOList = assessService.getAssessStruct(courseId);
        log.info("return: {}", assessStructVOList);
        return CommonResult.success(assessStructVOList);
    }

    @PostMapping(value = "/updateAssessStruct")
    @ApiOperation("批量更新课程考核结构")
    @ResponseBody
    public CommonResult<Boolean> mUpdateAssessStruct(@RequestBody @Valid ValidList<UpdateAssessStructDTO> updateAssessStructDTOList) {
        log.info("——————————批量更新课程考核结构——————————");
        for (UpdateAssessStructDTO each : updateAssessStructDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = assessService.mUpdateAssessStruct(updateAssessStructDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteAssessStruct")
    @ApiOperation("批量删除课程考核结构")
    @ResponseBody
    public CommonResult<Boolean> mDeleteAssessStruct(@RequestBody @Valid DeleteAssessStructDTO deleteAssessStructDTO) {
        log.info("———————————批量删除课程考核结构—————————————");
        Boolean result = assessService.mDeleteAssessStruct(deleteAssessStructDTO.getAssessStructIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertAssessStruct")
    @ApiOperation("新增课程考核结构")
    @ResponseBody
    public CommonResult<Boolean> insertAssessStruct(@RequestBody @Valid CreateAssessStructDTO createAssessStructDTO) {
        log.info("——————————新增课程考核结构——————————");
        if (!createAssessStructDTO.isValid()) {
            return CommonResult.validateFailed("权重必须大于0");
        }
        Boolean result = assessService.insertAssessStruct(createAssessStructDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @GetMapping(value = "/getIloAssessStruct")
    @ApiOperation("获取预期学习结果-课程考核结构")
    @ResponseBody
    public CommonResult<List<IloAssessStructVO>> getIloAssessStruct(@RequestParam("assess_struct_id")
                                                                    @ApiParam("课程考核结构id")
                                                                    @NotNull(message = "assess_struct_id不能为空")
                                                                    @PositiveOrZero(message = "assess_struct_id不能为负数")
                                                                    Long assessStructId) {
        log.info("——————————获取预期学习结果-课程考核结构——————————");
        log.info("assessStructId: {}", assessStructId);
        List<IloAssessStructVO> iloAssessStructVOList = assessService.getIloAssessStruct(assessStructId);
        log.info("return: {}", iloAssessStructVOList);
        return CommonResult.success(iloAssessStructVOList);
    }

    @PostMapping(value = "/deleteIloAssessStruct")
    @ApiOperation("批量删除预期学习结果-课程考核结构")
    @ResponseBody
    public CommonResult<Boolean> mDeleteIloAssessStruct(@RequestBody @Valid ValidList<DeleteIloAssessStructDTO> deleteIloAssessStructDTOList) {
        log.info("———————————批量删除预期学习结果-课程考核结构—————————————");
        Boolean result = assessService.mDeleteIloAssessStruct(deleteIloAssessStructDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/updateIloAssessStruct")
    @ApiOperation("批量更新预期学习结果-课程考核结构")
    @ResponseBody
    public CommonResult<Boolean> mUpdateIloAssessStruct(@RequestBody @Valid ValidList<UpdateIloAssessStructDTO> updateIloAssessStructDTOList) {
        log.info("——————————批量更新预期学习结果-教学课程考核结构——————————");
        for (UpdateIloAssessStructDTO each : updateIloAssessStructDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = assessService.mUpdateIloAssessStruct(updateIloAssessStructDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed("可能是预期学习结果或课程考核结构不存在");
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertIloAssessStruct")
    @ApiOperation("新增预期学习结果-教学课程考核结构")
    @ResponseBody
    public CommonResult<Boolean> insertIloAssessStruct(@RequestBody @Valid CreateIloAssessStructDTO createIloAssessStructDTO) {
        log.info("——————————新增预期学习结果-教学课程考核结构——————————");
        Boolean result = assessService.insertIloAssessStruct(createIloAssessStructDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed("可能是预期学习结果或课程考核结构不存在");
        }
        return CommonResult.success(true);
    }

}
