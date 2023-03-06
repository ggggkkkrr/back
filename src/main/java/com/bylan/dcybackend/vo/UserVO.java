package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 好友VO
 *
 * @author wuhuaming
 * @date 2022/3/31 11:22
 */
@ApiModel("好友VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    @ApiModelProperty("好友id")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty("好友姓名")
    @JsonProperty("user_name")
    private String userName;

    @ApiModelProperty("类型")
    @JsonProperty("role_type")
    private Integer type;

}
