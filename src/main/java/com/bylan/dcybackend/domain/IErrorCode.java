package com.bylan.dcybackend.domain;

/**
 * 常用API返回对象接口
 *
 * @author Rememorio
 */
public interface IErrorCode {
    /**
     * 返回码
     *
     * @return 错误
     */
    long getCode();

    /**
     * 返回信息
     *
     * @return 错误信息
     */
    String getMessage();
}
