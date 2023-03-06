package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 知识点达成度
 *
 * @author Rememorio
 */
@ApiModel("知识点达成度")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeReach {

    @ApiModelProperty("知识点id")
    private Long knowledgeId;
    @ApiModelProperty("周次")
    private Long week;
    @ApiModelProperty("达成度类型")
    private Integer reachType;
    @ApiModelProperty("达成度类型对应实体的id")
    private String entityId;
    @ApiModelProperty("知识点达成度分数")
    private Double knowledgeReachScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public KnowledgeReach(Long knowledgeId, Long week, Integer reachType, String entityId, Double knowledgeReachScore) {
        this.knowledgeId = knowledgeId;
        this.week = week;
        this.reachType = reachType;
        this.entityId = entityId;
        this.knowledgeReachScore = knowledgeReachScore;
    }
}
