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
 * 更新实践DTO
 *
 * @author Rememorio
 */
@ApiModel("更新实践DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePracticeDTO implements Serializable {

    @NotNull(message = "实践id不能为空")
    @PositiveOrZero(message = "实践id不能为负数")
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

    public Boolean isValid() {
        return practiceNumber != null || practiceDesc != null || classHour != null || contentTarget != null
                || task != null || assessmentForm != null || scoreStandard != null || remark != null;
    }

}
