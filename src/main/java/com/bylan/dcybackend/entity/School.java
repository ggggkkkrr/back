package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 学院
 *
 * @author Rememorio
 */
@ApiModel("学院")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {

    @ApiModelProperty("学院id")
    private Long schoolId;
    @ApiModelProperty("学院名称")
    private String schoolName;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
