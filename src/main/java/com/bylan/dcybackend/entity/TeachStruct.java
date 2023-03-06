package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.dto.CreateTeachStructDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教学环节结构
 *
 * @author Rememorio
 */
@ApiModel("教学环节结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachStruct {

    @ApiModelProperty("教学环节结构id")
    private Long teachStructId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("教学环节结构描述")
    private String teachStructDesc;
    @ApiModelProperty("课内学时")
    private Long inClassHour;
    @ApiModelProperty("课外学时")
    private Long afterClassHour;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public TeachStruct(Long courseId, String teachStructDesc) {
        this.courseId = courseId;
        this.teachStructDesc = teachStructDesc;
    }

    public TeachStruct(CreateTeachStructDTO createTeachStructDTO) {
        this.courseId = createTeachStructDTO.getCourseId();
        this.teachStructDesc = createTeachStructDTO.getTeachStructDesc();
        this.inClassHour = createTeachStructDTO.getInClassHour();
        this.afterClassHour = createTeachStructDTO.getAfterClassHour();
    }

}
