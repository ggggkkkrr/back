package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * @author wuhuaming
 */
@ApiModel("教师评价DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEvaluationDTO implements Serializable {

    @NotNull(message = "task_id不能为空")
    @PositiveOrZero(message = "task_id不能为负数")
    @ApiModelProperty("任务id")
    @JsonProperty("task_id")
    private Long taskId;

    @NotBlank(message = "学生学号不能为空")
    @ApiModelProperty("学生学号")
    @JsonProperty("student_id")
    private String studentId;

    @NotBlank(message = "教师评价不能为空")
    @ApiModelProperty("教师评价")
    @JsonProperty("evaluation")
    private String evaluation;

}
