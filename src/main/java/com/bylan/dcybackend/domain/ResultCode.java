package com.bylan.dcybackend.domain;

/**
 * 常用API返回对象
 *
 * @author Rememorio
 */
public enum ResultCode implements IErrorCode {
    // 操作成功
    SUCCESS(200, "操作成功"),
    // 操作失败
    FAILED(500, "操作失败"),
    // 暂未登录或token已经过期
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    // 没有相关权限
    FORBIDDEN(402, "没有相关权限"),
    // 参数检验失败
    VALIDATE_FAILED(403, "参数检验失败"),
    // 没有找到
    NOT_FOUND(404, "没有找到");


    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
