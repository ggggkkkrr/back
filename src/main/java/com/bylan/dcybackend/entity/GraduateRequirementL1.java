package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 毕业一级要求
 *
 * @author wuhuaming
 */
@ApiModel("毕业一级要求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduateRequirementL1 {

    @ApiModelProperty("毕业一级要求id")
    private Long l1Id;
    @ApiModelProperty("毕业一级要求描述")
    private String l1Desc;
    @ApiModelProperty("毕业一级要求权重")
    private Double l1Weight;
    @ApiModelProperty("专业id")
    private Long majorId;
    @ApiModelProperty("毕业年份")
    private Long graduateYear;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
