package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CreateTeachDetailDTO;
import com.bylan.dcybackend.dto.DeleteTeachDetailDTO;
import com.bylan.dcybackend.dto.UpdateTeachDetailDTO;
import com.bylan.dcybackend.service.TeachDetailService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.TeachDetailVO;
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
 * 教学大纲的教学环节细则
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的教学环节细则", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class TeachDetailController {

    private static final Logger log = LogManager.getLogger(TeachDetailController.class);

    @Autowired
    TeachDetailService teachDetailService;

    @GetMapping(value = "/getTeachDetail")
    @ApiOperation("获取教学环节细则列表")
    @ResponseBody
    public CommonResult<List<TeachDetailVO>> getTeachDetail(@RequestParam("course_id")
                                                            @ApiParam("课程id")
                                                            @NotNull(message = "course_id不能为空")
                                                            @PositiveOrZero(message = "course_id不能为负数")
                                                            Long courseId) {
        log.info("——————————获取教学环节细则列表——————————");
        log.info("courseId: {}", courseId);
        List<TeachDetailVO> teachDetailVOList = teachDetailService.getTeachDetail(courseId);
        log.info("return: {}", teachDetailVOList);
        return CommonResult.success(teachDetailVOList);
    }

    @PostMapping(value = "/updateTeachDetail")
    @ApiOperation("批量更新教学环节细则")
    @ResponseBody
    public CommonResult<Boolean> mUpdateTeachDetail(@RequestBody @Valid ValidList<UpdateTeachDetailDTO> updateTeachDetailDTOList) {
        log.info("——————————批量更新教学环节细则——————————");
        for (UpdateTeachDetailDTO each : updateTeachDetailDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = teachDetailService.mUpdateTeachDetail(updateTeachDetailDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteTeachDetail")
    @ApiOperation("批量删除教学细则结构")
    @ResponseBody
    public CommonResult<Boolean> mDeleteTeachDetail(@RequestBody @Valid DeleteTeachDetailDTO deleteTeachDetailDTO) {
        log.info("———————————批量删除教学环节细则—————————————");
        Boolean result = teachDetailService.mDeleteTeachDetail(deleteTeachDetailDTO.getTeachDetailIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertTeachDetail")
    @ApiOperation("新增教学环节细则")
    @ResponseBody
    public CommonResult<Boolean> insertTeachDetail(@RequestBody @Valid CreateTeachDetailDTO createTeachDetailDTO) {
        log.info("——————————新增教学环节细则——————————");
        Boolean result = teachDetailService.insertTeachDetail(createTeachDetailDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }
}
