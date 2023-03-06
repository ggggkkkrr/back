package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程本身
 *
 * @author Rememorio
 */
@ApiModel("课程本身")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curriculum {

    @ApiModelProperty("课程id")
    private Long curriculumId;
    @ApiModelProperty("课程代码")
    private String curriculumCode;
    @ApiModelProperty("设立年份")
    private Long setupYear;
    @ApiModelProperty("课程名称")
    private String curriculumName;
    @ApiModelProperty("课程描述")
    private String curriculumDesc;
    @ApiModelProperty("课程状态")
    private Integer curriculumStatus;
    @ApiModelProperty("课程类型")
    private Integer curriculumType;
    @ApiModelProperty("专业id")
    private Long majorId;
    @ApiModelProperty("学分")
    private Double credit;
    @ApiModelProperty("学时")
    private Long learningHour;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
