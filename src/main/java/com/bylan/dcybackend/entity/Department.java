package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系
 *
 * @author Rememorio
 */
@ApiModel("系")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @ApiModelProperty("系id")
    private Long departmentId;
    @ApiModelProperty("学院id")
    private Long schoolId;
    @ApiModelProperty("系名称")
    private String departmentName;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
