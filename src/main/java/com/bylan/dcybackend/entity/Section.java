package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教学班
 *
 * @author Rememorio
 */
@ApiModel("教学班")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @ApiModelProperty("教学班id")
    private Long sectionId;
    @ApiModelProperty("教学班名")
    private String sectionName;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
