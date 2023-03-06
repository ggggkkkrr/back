package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.Practice;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 实践VO
 *
 * @author Rememorio
 */
@ApiModel("实践VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeVO implements Serializable {

    @ApiModelProperty("实践id")
    @JsonProperty("practice_id")
    private Long practiceId;

    @ApiModelProperty("实践编号")
    @JsonProperty("practice_number")
    private String practiceNumber;

    @ApiModelProperty("实践描述")
    @JsonProperty("practice_desc")
    private String practiceDesc;

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

    public PracticeVO(Practice practice) {
        this.practiceId = practice.getPracticeId();
        this.practiceNumber = practice.getPracticeNumber();
        this.practiceDesc = practice.getPracticeDesc();
        this.classHour = practice.getClassHour();
        this.contentTarget = practice.getContentTarget();
        this.task = practice.getTask();
        this.assessmentForm = practice.getAssessmentForm();
        this.scoreStandard = practice.getScoreStandard();
        this.remark = practice.getRemark();
    }

}
