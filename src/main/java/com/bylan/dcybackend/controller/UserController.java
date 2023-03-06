package com.bylan.dcybackend.controller;

import com.bylan.dcybackend.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口
 *
 * @author Rememorio
 */
@Api(value = "用户接口", tags = {"用户"})
@RestController
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @GetMapping(value = "/info")
    @ApiOperation(value = "返回当前登录用户的信息", notes = "用户名")
    @ResponseBody
    public CommonResult<String> info() {
        log.info("——————————返回当前登录用户的信息——————————");
        String userDetails;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userDetails = ((UserDetails) principal).getUsername();
        } else {
            userDetails = principal.toString();
        }
        log.info("用户信息: {}", userDetails);
        return CommonResult.success(userDetails);
    }
}
