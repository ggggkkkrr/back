package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教学班达成度
 *
 * @author Rememorio
 */
@ApiModel("教学班达成度")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionReach {

    @ApiModelProperty("教学班id")
    private Long sectionId;
    @ApiModelProperty("周次")
    private Long week;
    @ApiModelProperty("教学班达成度分数")
    private Double sectionReachScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public SectionReach(Long sectionId, Long week, Double sectionReachScore) {
        this.sectionId = sectionId;
        this.week = week;
        this.sectionReachScore = sectionReachScore;
    }
}
