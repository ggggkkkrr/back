package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新建实践DTO
 *
 * @author Rememorio
 */
@ApiModel("新建实践DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePracticeDTO implements Serializable {

    @NotNull(message = "课程id不能为空")
    @Min(value = 0, message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotBlank(message = "实践序号不能为空")
    @ApiModelProperty("实践编号")
    @JsonProperty("practice_number")
    private String practiceNumber;

    @NotBlank(message = "实践名称不能为空")
    @ApiModelProperty("实践描述")
    @JsonProperty("practice_desc")
    private String practiceDesc;

    @NotNull(message = "学时不能为空")
    @Min(value = 0, message = "学时不符合范围")
    @Max(value = 255, message = "学时不符合范围")
    @ApiModelProperty("课时")
    @JsonProperty("class_hour")
    private Long classHour;

    @NotBlank(message = "实践内容与目标不能为空")
    @ApiModelProperty("活动内容与目标")
    @JsonProperty("content_target")
    private String contentTarget;

    @NotBlank(message = "实践任务不能为空")
    @ApiModelProperty("活动任务")
    @JsonProperty("task")
    private String task;

    @NotBlank(message = "考核形式与要求不能为空")
    @ApiModelProperty("考核形式")
    @JsonProperty("assessment_form")
    private String assessmentForm;

    @NotBlank(message = "评分标准不能为空")
    @ApiModelProperty("评分标准")
    @JsonProperty("score_standard")
    private String scoreStandard;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

}