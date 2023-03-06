package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 好友消息状态
 *
 * @author wuhuaming
 * @date 2022/3/31 13:52
 */
@ApiModel("好友消息状态")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendMsgStatusVO implements Serializable {

    @ApiModelProperty("学生学号")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty("状态")
    @JsonProperty("status")
    private Boolean status;
}
