package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 毕业二级要求
 *
 * @author wuhuaming
 */
@ApiModel("毕业二级要求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduateRequirementL2 {

    @ApiModelProperty("毕业二级要求id")
    private Long l2Id;
    @ApiModelProperty("毕业二级要求描述")
    private String l2Desc;
    @ApiModelProperty("毕业一级要求id")
    private Long l1Id;
    @ApiModelProperty("毕业二级要求权重")
    private Double l2Weight;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public GraduateRequirementL2(String l2Desc) {
        this.l2Desc = l2Desc;
    }
}
