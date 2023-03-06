package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Rememorio
 */
@ApiModel("题目分数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionScore {

    @ApiModelProperty("学生学号")
    private String studentId;
    @ApiModelProperty("题目id")
    private Long questionId;
    @ApiModelProperty("任务id")
    private Long taskId;
    @ApiModelProperty("题目得分")
    private Double questionScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
