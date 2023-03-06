package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CreateTeachStructDTO;
import com.bylan.dcybackend.dto.DeleteTeachStructDTO;
import com.bylan.dcybackend.dto.UpdateTeachStructDTO;
import com.bylan.dcybackend.service.TeachStructService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.TeachStructVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 教学大纲的教学环节结构
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的教学环节结构", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class TeachStructController {

    private static final Logger log = LogManager.getLogger(TeachStructController.class);

    @Autowired
    TeachStructService teachStructService;

    @GetMapping(value = "/getTeachStruct")
    @ApiOperation("获取教学环节结构列表")
    @ResponseBody
    public CommonResult<List<TeachStructVO>> getTeachStruct(@RequestParam("course_id")
                                                            @ApiParam("课程id")
                                                            @NotNull(message = "course_id不能为空")
                                                            @Min(value = 0, message = "course_id不能为负数")
                                                            Long courseId) {
        log.info("——————————获取教学环节结构列表——————————");
        log.info("courseId: {}", courseId);
        List<TeachStructVO> teachStructVOList = teachStructService.getTeachStruct(courseId);
        log.info("return: {}", teachStructVOList);
        return CommonResult.success(teachStructVOList);
    }

    @PostMapping(value = "/updateTeachStruct")
    @ApiOperation("批量更新教学环节结构")
    @ResponseBody
    public CommonResult<Boolean> mUpdateTeachStruct(@RequestBody @Valid ValidList<UpdateTeachStructDTO> updateTeachStructDTOList) {
        log.info("——————————批量更新教学环节结构——————————");
        for (UpdateTeachStructDTO each : updateTeachStructDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = teachStructService.mUpdateTeachStruct(updateTeachStructDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteTeachStruct")
    @ApiOperation("批量删除教学环节结构")
    @ResponseBody
    public CommonResult<Boolean> mDeleteTeachStruct(@RequestBody @Valid DeleteTeachStructDTO deleteTeachStructDTO) {
        log.info("———————————批量删除教学环节结构—————————————");
        Boolean result = teachStructService.mDeleteTeachStruct(deleteTeachStructDTO.getTeachStructIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertTeachStruct")
    @ApiOperation("新增教学环节结构")
    @ResponseBody
    public CommonResult<Boolean> insertTeachStruct(@RequestBody @Valid CreateTeachStructDTO createTeachStructDTO) {
        log.info("——————————新增教学环节结构——————————");
        Boolean result = teachStructService.insertTeachStruct(createTeachStructDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }
}
