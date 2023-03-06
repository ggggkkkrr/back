package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.domain.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Hello World
 *
 * @author Rememorio
 */
@Api(value = "Hello World测试接口", tags = {"测试"})
@RequestMapping("hello")
@RestController
public class HelloWorldController {

    private static final Logger log = LogManager.getLogger(HelloWorldController.class);

    @GetMapping(value = "/world")
    @ApiOperation(value = "返回hello world", notes = "没有什么特殊含义")
    @ResponseBody
    public CommonResult<String> helloWorld() {
        log.info("——————————返回hello world——————————");
        String result = Constant.Public.HELLO_WORLD;
        log.info("return: {}", result);
        return CommonResult.success(result);
    }

    @GetMapping(value = "/{name}")
    @ApiOperation(value = "返回打招呼的内容", notes = "也没有什么特殊含义")
    @ResponseBody
    public CommonResult<String> helloName(@PathVariable("name") @ApiParam("名称") String name) {
        log.info("——————————返回hello name——————————");
        String result = Constant.Public.HELLO + " " + name;
        log.info("return: {}", result);
        return CommonResult.success(result);
    }

    @PostMapping(value = "/content")
    @ApiOperation(value = "返回打招呼的内容", notes = "也没有什么特殊含义")
    @ResponseBody
    public CommonResult<String> helloContent(@ApiParam("名称") @RequestBody Map<String, String> contentMap) {
        log.info("——————————返回hello content——————————");
        if (contentMap.isEmpty() || contentMap.get(Constant.Public.NAME).isEmpty()) {
            log.warn("request param is empty");
            return CommonResult.validateFailed();
        }
        String result = Constant.Public.HELLO + " " + contentMap.get(Constant.Public.NAME);
        log.info("return: {}", result);
        return CommonResult.success(result);
    }

}