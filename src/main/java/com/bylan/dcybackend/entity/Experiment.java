package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.dto.CreateExperimentDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 实验
 *
 * @author Rememorio
 */
@ApiModel("实验")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experiment {

    @ApiModelProperty("实验id")
    private Long experimentId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("实验编号")
    private String experimentNumber;
    @ApiModelProperty("实验描述")
    private String experimentDesc;
    @ApiModelProperty("课时")
    private Long classHour;
    @ApiModelProperty("活动内容与目标")
    private String contentTarget;
    @ApiModelProperty("活动任务")
    private String task;
    @ApiModelProperty("考核形式")
    private String assessmentForm;
    @ApiModelProperty("评分标准")
    private String scoreStandard;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public Experiment(CreateExperimentDTO createExperimentDTO) {
        this.courseId = createExperimentDTO.getCourseId();
        this.experimentNumber = createExperimentDTO.getExperimentNumber();
        this.experimentDesc = createExperimentDTO.getExperimentDesc();
        this.classHour = createExperimentDTO.getClassHour();
        this.contentTarget = createExperimentDTO.getContentTarget();
        this.task = createExperimentDTO.getTask();
        this.assessmentForm = createExperimentDTO.getAssessmentForm();
        this.scoreStandard = createExperimentDTO.getScoreStandard();
        this.remark = createExperimentDTO.getRemark();
    }

}
