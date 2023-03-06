package com.bylan.dcybackend.netty;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuhuaming
 * @date 2022/06/04 17:33
 */
@Data
public class DataContent {

    @ApiModelProperty("任务类型")
    @JSONField(name = "action")
    private Integer action;

    @ApiModelProperty("消息内容")
    @JSONField(name = "chat_msg")
    private ChatMsg chatMsg;

    @ApiModelProperty("扩展字段")
    @JSONField(name = "extent")
    private String extent;
}
