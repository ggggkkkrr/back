package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CreateExperimentDTO;
import com.bylan.dcybackend.dto.DeleteExperimentDTO;
import com.bylan.dcybackend.dto.UpdateExperimentDTO;
import com.bylan.dcybackend.service.ExperimentService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.ExperimentVO;
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
 * 教学大纲的实验
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的实验", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class ExperimentController {

    private static final Logger log = LogManager.getLogger(ExperimentController.class);

    @Autowired
    ExperimentService experimentService;

    @GetMapping(value = "/getExperiment")
    @ApiOperation("获取实验列表")
    @ResponseBody
    public CommonResult<List<ExperimentVO>> getExperiment(@RequestParam("course_id")
                                                          @ApiParam("课程id")
                                                          @NotNull(message = "course_id不能为空")
                                                          @PositiveOrZero(message = "course_id不能为负数")
                                                          Long courseId) {
        log.info("——————————获取实验列表——————————");
        log.info("courseId: {}", courseId);
        List<ExperimentVO> experimentVOList = experimentService.getExperiment(courseId);
        log.info("return: {}", experimentVOList);
        return CommonResult.success(experimentVOList);
    }

    @PostMapping(value = "/updateExperiment")
    @ApiOperation("批量更新实验")
    @ResponseBody
    public CommonResult<Boolean> mUpdateExperiment(@RequestBody @Valid ValidList<UpdateExperimentDTO> updateExperimentDTOList) {
        log.info("——————————批量更新实验——————————");
        for (UpdateExperimentDTO each : updateExperimentDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = experimentService.mUpdateExperiment(updateExperimentDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteExperiment")
    @ApiOperation("批量删除实验")
    @ResponseBody
    public CommonResult<Boolean> mDeleteExperiment(@RequestBody @Valid DeleteExperimentDTO deleteExperimentDTO) {
        log.info("———————————批量删除实验—————————————");
        Boolean result = experimentService.mDeleteExperiment(deleteExperimentDTO.getExperimentIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertExperiment")
    @ApiOperation("新增实验")
    @ResponseBody
    public CommonResult<Boolean> insertExperiment(@RequestBody @Valid CreateExperimentDTO createExperimentDTO) {
        log.info("——————————新增实验——————————");
        Boolean result = experimentService.insertExperiment(createExperimentDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

}
