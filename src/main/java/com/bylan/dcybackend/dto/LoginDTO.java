package com.bylan.dcybackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户登录的DTO
 *
 * @author Rememorio
 */
@ApiModel("登录DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

    @NotNull(message = "用户名不能为空")
    @Size(min = 4, max = 31, message = "用户名长度必须在4到31字符之间")
    @ApiModelProperty("用户名")
    @JsonProperty("username")
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 4, max = 100, message = "密码长度必须在8到100字符之间")
    @ApiModelProperty("密码")
    @JsonProperty("password")
    private String password;

    @ApiModelProperty("记住我")
    @JsonProperty("remember_me")
    private Boolean rememberMe;

    @NotNull(message = "uuid不能为空")
    @ApiModelProperty("生成验证码的随机串")
    @JsonProperty("uuid")
    private String uuid;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty("用户输入的验证码")
    @JsonProperty("code")
    private String inputCode;

}
