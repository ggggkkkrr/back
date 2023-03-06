package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 预期学习结果达成度
 *
 * @author Rememorio
 */
@ApiModel("预期学习结果达成度")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloReach {

    @ApiModelProperty("预期学习结果id")
    private Long iloId;
    @ApiModelProperty("周次")
    private Long week;
    @ApiModelProperty("达成度类型")
    private Integer reachType;
    @ApiModelProperty("达成度类型对应实体的id")
    private String entityId;
    @ApiModelProperty("预期学习结果达成度分数")
    private Double iloReachScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public IloReach(Long iloId, Long week, Integer reachType, String entityId, Double iloReachScore) {
        this.iloId = iloId;
        this.week = week;
        this.reachType = reachType;
        this.entityId = entityId;
        this.iloReachScore = iloReachScore;
    }
}
