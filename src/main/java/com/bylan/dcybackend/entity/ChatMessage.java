package com.bylan.dcybackend.entity;

import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.MessageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wuhuaming
 */
@ApiModel("聊天消息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @ApiModelProperty("消息id")
    private Long messageId;
    @ApiModelProperty("班级id")
    private Long sectionId;
    @ApiModelProperty("收d")
    private String toId;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("消息类型")
    private Integer messageType;
    @ApiModelProperty("发id")
    private String fromId;
    @ApiModelProperty("发的名称")
    private String fromName;
    @ApiModelProperty("消息状态")
    private Integer messageStatus;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public ChatMessage(MessageDTO messageDTO) {
        this.messageType = messageDTO.getMessageType();
        this.content = messageDTO.getContent();
        this.fromId = messageDTO.getFromId();
        this.fromName = messageDTO.getFromName();
        this.toId = messageDTO.getToId();
        this.sectionId = messageDTO.getSectionId();
        this.createTime = messageDTO.getCreateTime();
        this.messageStatus = Constant.Chat.NORMAL;
    }

    public ChatMessage(Integer messageType,String content, String fromId, String fromName, String toId, Long sectionId, Date createTime) {
        this.messageType = messageType;
        this.content = content;
        this.fromId = fromId;
        this.fromName = fromName;
        this.toId = toId;
        this.sectionId = sectionId;
        this.createTime = createTime;
        this.messageStatus = Constant.Chat.NORMAL;
    }
}
