package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 更新实验DTO
 *
 * @author Rememorio
 */
@ApiModel("更新实验DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExperimentDTO implements Serializable {

    @NotNull(message = "实验id不能为空")
    @PositiveOrZero(message = "实验id不能为负数")
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

    public Boolean isValid() {
        return experimentNumber != null || experimentDesc != null || classHour != null || contentTarget != null
                || task != null || assessmentForm != null || scoreStandard != null || remark != null;
    }

}
