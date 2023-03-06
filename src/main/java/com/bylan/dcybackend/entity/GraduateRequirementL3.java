package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 毕业三级要求
 *
 * @author Rememorio
 */
@ApiModel("毕业三级要求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduateRequirementL3 {

    @ApiModelProperty("毕业三级要求id")
    private Long l3Id;
    @ApiModelProperty("毕业三级要求描述")
    private String l3Desc;
    @ApiModelProperty("毕业三级要求权重")
    private Double l3Weight;
    @ApiModelProperty("毕业二级要求id")
    private Long l2Id;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public GraduateRequirementL3(String l3Desc) {
        this.l3Desc = l3Desc;
    }

}
