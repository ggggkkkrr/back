package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教学大纲
 *
 * @author Rememorio
 */
@ApiModel("教学大纲")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Syllabus {

    @ApiModelProperty("教学大纲id")
    private Long syllabusId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("教学大纲路径")
    private String syllabusPath;
    @ApiModelProperty("教学大纲版本")
    private String syllabusVersion;
    @ApiModelProperty("教学大纲状态")
    private Long syllabusStatus;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
