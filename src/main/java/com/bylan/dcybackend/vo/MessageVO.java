package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息VO
 *
 * @author wuhuaming
 * @date 2022/4/1 16:35
 */
@ApiModel("消息VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO implements Serializable, Comparable<MessageVO> {
    @ApiModelProperty("消息id")
    @JsonProperty("id")
    private Long messageId;
    @ApiModelProperty("接收者id")
    @JsonProperty("to_id")
    private String toId;
    @ApiModelProperty("内容")
    @JsonProperty("content")
    private String content;
    @ApiModelProperty("消息类型")
    @JsonProperty("message_type")
    private Integer messageType;
    @ApiModelProperty("发送者id")
    @JsonProperty("from_id")
    private String fromId;
    @ApiModelProperty("发送者姓名")
    @JsonProperty("from_name")
    private String fromName;
    @ApiModelProperty("消息状态")
    @JsonProperty("message_status")
    private Integer messageStatus;
    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @Override
    public int compareTo(MessageVO o) {
        return (int) (messageId - o.getMessageId());
    }
}
