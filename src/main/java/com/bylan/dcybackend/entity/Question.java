package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 题目
 *
 * @author Rememorio
 */
@ApiModel("题目")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @ApiModelProperty("题目id")
    private Long questionId;
    @ApiModelProperty("任务id")
    private Long taskId;
    @ApiModelProperty("题目名称")
    private String questionName;
    @ApiModelProperty("题目描述")
    private String questionDesc;
    @ApiModelProperty("题目分数")
    private Double questionScore;
    @ApiModelProperty("题目平均分")
    private Double questionAverageScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
