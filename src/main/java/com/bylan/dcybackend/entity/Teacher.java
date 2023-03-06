package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教师
 *
 * @author Rememorio
 */
@ApiModel("教师")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @ApiModelProperty("教师工号")
    private String teacherId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("网址")
    private String url;
    @ApiModelProperty("入职年份")
    private Long enrollmentYear;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
