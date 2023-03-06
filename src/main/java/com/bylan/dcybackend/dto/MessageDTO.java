package com.bylan.dcybackend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wuhuaming
 * @date 2022/3/29 10:11
 */
@ApiModel("消息DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  MessageDTO {

    @ApiModelProperty("发送者id")
    @JSONField(name = "from_id")
    private String fromId;

    @ApiModelProperty("发送者名字")
    @JSONField(name = "from_name")
    private String fromName;

    @ApiModelProperty("接收者者id")
    @JSONField(name = "to_id")
    private String toId;

    @ApiModelProperty("内容")
    @JSONField(name = "content")
    private String content;

    @ApiModelProperty("创建时间")
    @JSONField(name = "create_time")
    private Date createTime;

    @ApiModelProperty("教学班id")
    @JSONField(name = "section_id")
    private Long sectionId;

    @ApiModelProperty("消息类型 文字、图片")
    @JSONField(name = "message_type")
    private Integer MessageType;

    @ApiModelProperty("消息id")
    @JSONField(name = "id")
    private Long id;

    @ApiModelProperty("后端发送的类型")
    @JSONField(name = "type")
    private Integer type;

    @ApiModelProperty("操作者 用于适配撤回类型的消息")
    @JSONField(name = "operator")
    private String operator;

}
