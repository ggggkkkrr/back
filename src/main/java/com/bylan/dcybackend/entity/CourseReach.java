package com.bylan.dcybackend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程达成度
 *
 * @author Rememorio
 */
@ApiModel("课程达成度")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReach {

    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("周次")
    private Long week;
    @ApiModelProperty("整个课程的达成度")
    private Double courseReachScore;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public CourseReach(Long courseId, Long week, Double courseReachScore) {
        this.courseId = courseId;
        this.week = week;
        this.courseReachScore = courseReachScore;
    }
}