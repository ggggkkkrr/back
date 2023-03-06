package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 知识点
 *
 * @author Rememorio
 */
@ApiModel("知识点")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgePoint {

    @ApiModelProperty("知识点id")
    private Long knowledgeId;
    @ApiModelProperty("预期学习结果id")
    private Long iloId;
    @ApiModelProperty("知识点编号")
    private String knowledgeNumber;
    @ApiModelProperty("知识点权重")
    private Double knowledgeWeight;
    @ApiModelProperty("知识点描述")
    private String knowledgeDesc;
    @ApiModelProperty("预期分数")
    private Double expectedScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
