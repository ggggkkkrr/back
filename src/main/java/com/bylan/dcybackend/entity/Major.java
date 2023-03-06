package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 专业
 *
 * @author Rememorio
 */
@ApiModel("专业")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major {

    @ApiModelProperty("专业id")
    private Long majorId;
    @ApiModelProperty("系id")
    private Long departmentId;
    @ApiModelProperty("专业代码")
    private String majorCode;
    @ApiModelProperty("专业名称")
    private String majorName;
    @ApiModelProperty("专业描述")
    private String majorDesc;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
