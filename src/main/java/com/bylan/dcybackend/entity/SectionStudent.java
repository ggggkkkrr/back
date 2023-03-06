package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教学班-学生
 *
 * @author Rememorio
 */
@ApiModel("教学班-学生")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionStudent {

    @ApiModelProperty("教学班id")
    private Long sectionId;
    @ApiModelProperty("学生学号")
    private String studentId;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
