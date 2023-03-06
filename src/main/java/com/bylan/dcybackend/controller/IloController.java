package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CreateIloDTO;
import com.bylan.dcybackend.dto.DeleteIloDTO;
import com.bylan.dcybackend.dto.UpdateIloDTO;
import com.bylan.dcybackend.service.IloService;
import com.bylan.dcybackend.utils.ValidList;
import com.bylan.dcybackend.vo.IloVO;
import com.bylan.dcybackend.vo.L1VO;
import com.bylan.dcybackend.vo.L2VO;
import com.bylan.dcybackend.vo.L3VO;
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
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 教学大纲的ILO
 *
 * @author Rememorio
 */
@Api(value = "教学大纲的ILO", tags = {"教学大纲"})
@RequestMapping("/syllabus")
@RestController
public class IloController {

    private static final Logger log = LogManager.getLogger(IloController.class);

    @Autowired
    IloService iloService;

    @GetMapping(value = "/getIlo")
    @ApiOperation("根据courseId获取ILO")
    @ResponseBody
    public CommonResult<List<IloVO>> mGetIlo(@RequestParam("course_id")
                                             @ApiParam("课程id")
                                             @NotNull(message = "course_id不能为空")
                                             @PositiveOrZero(message = "course_id不能为负数")
                                             Long courseId) {
        log.info("—————————查询ILO—————————");
        List<IloVO> iloVOList = iloService.mGetIloByCourseId(courseId);
        log.info("ILO: {}", iloVOList);
        return CommonResult.success(iloVOList);
    }

    @PostMapping(value = "/updateIlo")
    @ApiOperation("批量更新ILO")
    @ResponseBody
    public CommonResult<Boolean> mUpdateIlo(@RequestBody @Valid ValidList<UpdateIloDTO> updateIloDTOList) {
        log.info("——————————批量更新ILO——————————");
        for (UpdateIloDTO each : updateIloDTOList) {
            if (!each.isValid()) {
                return CommonResult.validateFailed();
            }
        }
        Boolean result = iloService.mUpdateIlo(updateIloDTOList);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/deleteIlo")
    @ApiOperation("批量删除ILO")
    @ResponseBody
    public CommonResult<Boolean> mDeleteIlo(@RequestBody @Valid DeleteIloDTO deleteIloDTO) {
        log.info("———————————批量删除ILO—————————————");
        Boolean result = iloService.mDeleteIlo(deleteIloDTO.getIloIds());
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

    @GetMapping(value = "/getL1")
    @ApiOperation("根据专业Id获取毕业要求一级")
    @ResponseBody
    public CommonResult<List<L1VO>> getL1(@RequestParam("major_id")
                                          @ApiParam("专业id")
                                          @NotNull(message = "major_id不能为空")
                                          @PositiveOrZero(message = "major_id不能为负数")
                                          Long majorId) {
        log.info("——————————获取毕业要求一级——————————");
        List<L1VO> l1VOList = iloService.getL1(majorId);
        log.info("毕业要求一级: {}", l1VOList);
        if (l1VOList.size() == 0) {
            return CommonResult.notFound();
        }
        return CommonResult.success(l1VOList);
    }

    @GetMapping(value = "/getL2")
    @ApiOperation("根据毕业要求一级Id获取毕业要求二级")
    @ResponseBody
    public CommonResult<List<L2VO>> getL2(@RequestParam("l1_id")
                                          @ApiParam("毕业要求一级id")
                                          @NotNull(message = "l1_id不能为空")
                                          @PositiveOrZero(message = "l1_id不能为负数")
                                          Long l1Id) {
        log.info("——————————获取毕业要求二级——————————");
        List<L2VO> l2VOList = iloService.getL2(l1Id);
        log.info("毕业要求二级: {}", l2VOList);
        if (l2VOList.size() == 0) {
            return CommonResult.notFound();
        }
        return CommonResult.success(l2VOList);
    }

    @GetMapping(value = "/getL3")
    @ApiOperation("根据毕业要求二级Id获取毕业要求三级")
    @ResponseBody
    public CommonResult<List<L3VO>> getL3(@RequestParam("l2_id")
                                          @ApiParam("毕业要求二级id")
                                          @NotNull(message = "l2_id不能为空")
                                          @Min(value = 0, message = "l2_id不能为负数")
                                          Long l2Id) {
        log.info("——————————获取毕业要求三级——————————");
        List<L3VO> l3VOList = iloService.getL3(l2Id);
        log.info("毕业要求三级: {}", l3VOList);
        if (l3VOList.size() == 0) {
            return CommonResult.notFound();
        }
        return CommonResult.success(l3VOList);
    }

    @PostMapping(value = "/insertIlo")
    @ApiOperation("新增ILO")
    @ResponseBody
    public CommonResult<Boolean> insertIlo(@RequestBody @Valid CreateIloDTO createIloDTO) {
        log.info("——————————新增ILO——————————");
        Boolean result = iloService.insertIlo(createIloDTO);
        log.info("操作结果: {}", result);
        if (!result) {
            return CommonResult.failed();
        }
        return CommonResult.success(true);
    }

}
