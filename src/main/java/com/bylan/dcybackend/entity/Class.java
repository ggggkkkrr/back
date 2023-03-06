package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 行政班
 *
 * @author Rememorio
 */
@ApiModel("行政班")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {

    @ApiModelProperty("行政班id")
    private Long classId;
    @ApiModelProperty("专业id")
    private Long majorId;
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("班主任id")
    private String teacherId;
    @ApiModelProperty("创建年份")
    private Long createYear;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
