package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.Experiment;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 实验VO
 *
 * @author Rememorio
 */
@ApiModel("实验VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentVO implements Serializable {

    @ApiModelProperty("实验id")
    @JsonProperty("experiment_id")
    private Long experimentId;

    @ApiModelProperty("实验编号")
    @JsonProperty("experiment_number")
    private String experimentNumber;

    @ApiModelProperty("实验描述")
    @JsonProperty("experiment_desc")
    private String experimentDesc;

    @ApiModelProperty("课时")
    @JsonProperty("class_hour")
    private Long classHour;

    @ApiModelProperty("活动内容与目标")
    @JsonProperty("content_target")
    private String contentTarget;

    @ApiModelProperty("活动任务")
    @JsonProperty("task")
    private String task;

    @ApiModelProperty("考核形式")
    @JsonProperty("assessment_form")
    private String assessmentForm;

    @ApiModelProperty("评分标准")
    @JsonProperty("score_standard")
    private String scoreStandard;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    public ExperimentVO(Experiment experiment) {
        this.experimentId = experiment.getExperimentId();
        this.experimentNumber = experiment.getExperimentNumber();
        this.experimentDesc = experiment.getExperimentDesc();
        this.classHour = experiment.getClassHour();
        this.contentTarget = experiment.getContentTarget();
        this.task = experiment.getTask();
        this.assessmentForm = experiment.getAssessmentForm();
        this.scoreStandard = experiment.getScoreStandard();
        this.remark = experiment.getRemark();
    }

}
