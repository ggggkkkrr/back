package com.bylan.dcybackend.handler;

import com.bylan.dcybackend.domain.CommonResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类
 *
 * @author Rememorio
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult<String> exceptionHandler(HttpServletRequest req, Exception e) {
        log.info("出现了异常");
        e.printStackTrace();
        log.info("异常信息：{}", e.getMessage());
        return CommonResult.failed(e.toString());
    }
}