package com.bylan.dcybackend.netty;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wuhuaming
 * @date 2022/06/04 17:35
 */
@Data
public class ChatMsg {

    @ApiModelProperty("发送者Id")
    @JSONField(name = "from_id")
    private String fromId;

    @ApiModelProperty("教学班编号")
    @JSONField(name = "section_id")
    private Long sectionId;

    @ApiModelProperty("接收者编号")
    @JSONField(name = "to_id")
    private String toId;

    @ApiModelProperty("发送者姓名")
    @JSONField(name = "from_name")
    private String fromName;

    @ApiModelProperty("消息内容")
    @JSONField(name = "content")
    private String content;

    @ApiModelProperty("创建时间")
    @JSONField(name = "create_time")
    private Date createTime;

    @ApiModelProperty("消息编号")
    @JSONField(name = "id")
    private Long id;

    @ApiModelProperty("消息类型")
    @JSONField(name = "message_type")
    private Integer messageType;

}
