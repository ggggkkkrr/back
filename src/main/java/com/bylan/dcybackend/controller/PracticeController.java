package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CreatePracticeDTO;
import com.bylan.dcybackend.dto.DeletePracticeDTO;
import com.bylan.dcybackend.dto.UpdatePracticeDTO;
import com.bylan.dcybackend.service.PracticeService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.PracticeVO;
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
 * 教学大纲的实践
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的实践", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class PracticeController {

    private static final Logger log = LogManager.getLogger(PracticeController.class);

    @Autowired
    PracticeService practiceService;

    @GetMapping(value = "/getPractice")
    @ApiOperation("获取实践列表")
    @ResponseBody
    public CommonResult<List<PracticeVO>> getPractice(@RequestParam("course_id")
                                                      @ApiParam("课程id")
                                                      @NotNull(message = "course_id不能为空")
                                                      @PositiveOrZero(message = "course_id不能为负数")
                                                      Long courseId) {
        log.info("——————————获取实践列表——————————");
        log.info("courseId: {}", courseId);
        List<PracticeVO> practiceVOList = practiceService.getPractice(courseId);
        log.info("return: {}", practiceVOList);
        return CommonResult.success(practiceVOList);
    }

    @PostMapping(value = "/updatePractice")
    @ApiOperation("批量更新实践")
    @ResponseBody
    public CommonResult<Boolean> mUpdatePractice(@RequestBody @Valid ValidList<UpdatePracticeDTO> updatePracticeDTOList) {
        log.info("——————————批量更新实践——————————");
        for (UpdatePracticeDTO each : updatePracticeDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = practiceService.mUpdatePractice(updatePracticeDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deletePractice")
    @ApiOperation("批量删除实践")
    @ResponseBody
    public CommonResult<Boolean> mDeletePractice(@RequestBody @Valid DeletePracticeDTO deletePracticeDTO) {
        log.info("———————————批量删除实践—————————————");
        Boolean result = practiceService.mDeletePractice(deletePracticeDTO.getPracticeIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/insertPractice")
    @ApiOperation("新增实践")
    @ResponseBody
    public CommonResult<Boolean> insertPractice(@RequestBody @Valid CreatePracticeDTO createPracticeDTO) {
        log.info("——————————新增实践——————————");
        Boolean result = practiceService.insertPractice(createPracticeDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

}
