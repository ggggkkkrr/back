package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 题目-知识点
 *
 * @author Rememorio
 */
@ApiModel("题目-知识点")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionKnowledgePoint {

    @ApiModelProperty("题目id")
    private Long questionId;
    @ApiModelProperty("知识点id")
    private Long knowledgeId;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
