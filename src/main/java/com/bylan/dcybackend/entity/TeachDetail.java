package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.bo.TeachDetailBO;
import com.bylan.dcybackend.dto.CreateTeachDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 教学环节细则
 *
 * @author Rememorio
 */
@ApiModel("教学环节细则")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachDetail {

    @ApiModelProperty("教学环节细则id")
    private Long teachDetailId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("教学环节细则权重")
    private Double teachDetailWeight;
    @ApiModelProperty("教学内容")
    private String teachContent;
    @ApiModelProperty("实现环节")
    private String implLink;
    @ApiModelProperty("教学策略")
    private String teachStrategy;
    @ApiModelProperty("毕业二级要求id")
    private Long l2Id;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public TeachDetail(TeachDetailBO teachDetailBO) {
        this.courseId = teachDetailBO.getCourseId();
        this.teachDetailWeight = teachDetailBO.getTeachDetailWeight();
        this.teachContent = teachDetailBO.getTeachContent();
        this.implLink = teachDetailBO.getImplLink();
        this.teachStrategy = teachDetailBO.getTeachStrategy();
    }

    public TeachDetail(CreateTeachDetailDTO createTeachDetailDTO) {
        this.courseId = createTeachDetailDTO.getCourseId();
        this.teachDetailWeight = createTeachDetailDTO.getTeachDetailWeight();
        this.teachContent = createTeachDetailDTO.getTeachContent();
        this.implLink = createTeachDetailDTO.getImplLink();
        this.teachStrategy = createTeachDetailDTO.getTeachStrategy();
        this.l2Id = createTeachDetailDTO.getL2Id();
    }

}
