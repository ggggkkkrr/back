package com.bylan.dcybackend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wuhuaming
 */
@ApiModel("上次查看时间")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageState {

    @ApiModelProperty("收d")
    private String toId;
    @ApiModelProperty("发id")
    private String fromId;
    @ApiModelProperty("班级id")
    private Long sectionId;
    @ApiModelProperty("读取时间")
    private Date readTime;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
