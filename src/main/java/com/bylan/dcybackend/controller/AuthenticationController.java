package com.bylan.dcybackend.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.bylan.dcybackend.config.jwt.JwtFilter;
import com.bylan.dcybackend.config.jwt.TokenProvider;
import com.bylan.dcybackend.domain.CommonResult;
import com.bylan.dcybackend.dto.CodeDTO;
import com.bylan.dcybackend.dto.LoginDTO;
import com.bylan.dcybackend.service.LoginService;
import com.bylan.dcybackend.vo.TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;

/**
 * 用户鉴权登录
 *
 * @author Rememorio
 */
@Api(value = "鉴权登录", tags = {"鉴权"})
@RequestMapping("/login")
@RestController
public class AuthenticationController {

    private static final Logger log = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private LoginService loginService;

    private final boolean flag = "dev".equals(SpringUtil.getActiveProfile());

    @PostMapping(value = "/authenticate")
    @ApiOperation(value = "鉴权登录", notes = "返回一个token，由前端保管")
    @ResponseBody
    public ResponseEntity<CommonResult<TokenVO>> authorize(@RequestBody @Valid LoginDTO loginDto) {
        log.info("——————————鉴权登录——————————");
        log.info("loginDto: {}", loginDto);

        // 如果不是dev环境，先校验验证码
        if (!flag) {
            Boolean success = loginService.verifyVerificationCode(loginDto.getUuid(), loginDto.getInputCode());
            log.info("code verification result: {}", success);
            if (!success) {
                return new ResponseEntity<>(CommonResult.failed(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = loginDto.getRememberMe() != null && loginDto.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER + jwt);
        log.info("jwt: {}", jwt);

        // 获取账号角色
        String[] roles = loginService.getRole(loginDto.getUsername());
        log.info("roles: {}", Arrays.toString(roles));

        return new ResponseEntity<>(CommonResult.success(new TokenVO(jwt, roles)), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/getCode")
    @ApiOperation(value = "获取图片验证码", notes = "有效期一分钟")
    @ResponseBody
    public void getVerificationCode(@RequestBody @Valid CodeDTO codeDTO, HttpServletResponse response) {
        log.info("——————————获取图片验证码——————————");
        try {
            loginService.getVerificationCode(codeDTO.getUuid(), response);
        } catch (IOException e) {
            log.info("failed to get verification code");
            return;
        }
        log.info("get verification code successfully");
    }

    @PostMapping(value = "/verifyCode")
    @ApiOperation(value = "校验图片验证码", notes = "需要传入与生成验证码操作相同的uuid，这个接口仅供单独测试使用，实际直接使用鉴权接口")
    @ResponseBody
    public CommonResult<Boolean> verifyVerificationCode(@RequestBody @Validated(value = {CodeDTO.CodeNullable.class}) CodeDTO codeDTO) {
        log.info("——————————校验图片验证码——————————");
        Boolean result = loginService.verifyVerificationCode(codeDTO.getUuid(), codeDTO.getCode());
        log.info("return: {}", result);
        return CommonResult.success(result);
    }
}
