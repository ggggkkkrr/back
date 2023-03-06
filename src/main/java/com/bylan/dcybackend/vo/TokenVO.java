package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * token VO
 *
 * @author Rememorio
 */
@ApiModel("token的VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO implements Serializable {

    @ApiModelProperty("token")
    @JsonProperty("token")
    private String token;

    @ApiModelProperty("角色")
    @JsonProperty("role")
    private String[] roles;

}
