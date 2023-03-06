package com.bylan.dcybackend.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录服务
 *
 * @author Rememorio
 */
public interface LoginService {
    /**
     * 获取教师角色
     *
     * @param teacherId 教师工号
     * @return 教师角色数组
     */
    String[] getRole(String teacherId);

    /**
     * 获取验证码
     *
     * @param uuid     uuid
     * @param response 响应
     * @throws IOException IO异常
     */
    void getVerificationCode(String uuid, HttpServletResponse response) throws IOException;

    /**
     * 校验验证码
     *
     * @param uuid      uuid
     * @param inputCode 输入的验证码
     * @return 是否通过
     */
    boolean verifyVerificationCode(String uuid, String inputCode);
}
