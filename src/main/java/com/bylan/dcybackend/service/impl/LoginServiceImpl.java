package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.AccountDAO;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.service.LoginService;
import com.bylan.dcybackend.utils.RedisUtil;
import com.bylan.dcybackend.utils.VerificationCodeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务实现
 *
 * @author Rememorio
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LogManager.getLogger(LoginServiceImpl.class);

    @Autowired
    AccountDAO accountDao;

    /**
     * 获取教师角色
     *
     * @param teacherId 教师工号
     * @return 教师角色数组
     */
    @Override
    public String[] getRole(String teacherId) {
        if (teacherId == null) {
            return new String[0];
        }
        String role = accountDao.getRoleByAccountId(teacherId);
        String[] roles = role.split(Constant.Public.SEPARATOR);
        log.info("角色role: {}, 角色数组roles: {}", role, roles);
        return roles;
    }

    /**
     * 获取验证码
     *
     * @param uuid     uuid
     * @param response 响应
     * @throws IOException IO异常
     */
    @Override
    public void getVerificationCode(String uuid, HttpServletResponse response) throws IOException {
        // 禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 获取验证码
        String code = VerificationCodeUtil.generateVerifyCode(4);
        // 验证码已key，value的形式缓存到redis 存放时间一分钟
        RedisUtil.set(uuid, code, 1, TimeUnit.MINUTES);
        log.info("设置验证码，uuid: {}, code: {}", uuid, code);
        ServletOutputStream outputStream = response.getOutputStream();
        VerificationCodeUtil.outputImage(110, 40, outputStream, code);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 校验验证码
     *
     * @param uuid      uuid
     * @param inputCode 输入的验证码
     * @return 是否通过
     */
    @Override
    public boolean verifyVerificationCode(String uuid, String inputCode) {
        String code = (String) RedisUtil.get(uuid);
        if (code == null) {
            log.warn("the value of uuid {} is null", uuid);
            return false;
        }
        log.info("获取验证码，uuid: {}, code: {}", uuid, code);
        log.info("input code: {}", inputCode);
        return code.equals(inputCode);
    }
}
