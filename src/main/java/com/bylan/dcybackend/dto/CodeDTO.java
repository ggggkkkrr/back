package com.bylan.dcybackend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 验证码DTO
 *
 * @author Rememorio
 */
@ApiModel("验证码DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeDTO implements Serializable {

    @NotBlank(message = "uuid不能为空")
    @ApiModelProperty("uuid")
    private String uuid;

    // 指定group之后，只有带group的validated才强制需要非空

    @NotBlank(message = "验证码不能为空", groups = {CodeNullable.class})
    @Size(max = 4, message = "验证码长度不符合范围")
    @ApiModelProperty("验证码")
    private String code;

    public interface CodeNullable {
    }
}
