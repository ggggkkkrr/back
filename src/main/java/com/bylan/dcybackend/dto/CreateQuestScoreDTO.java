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
 * 创建题目得分DTO
 *
 * @author wuhuaming
 */
@ApiModel("创建题目得分DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestScoreDTO implements Serializable {

    @NotBlank(message = "学生编号不能为空")
    @ApiModelProperty("学生编号")
    @JsonProperty("student_id")
    private String studentId;

    @NotNull(message = "题目编号不能为空")
    @PositiveOrZero(message = "题目编号不能为负数")
    @ApiModelProperty("题目编号")
    @JsonProperty("question_id")
    private Long questionId;

    @NotNull(message = "任务编号不能为空")
    @PositiveOrZero(message = "任务编号不能为负数")
    @ApiModelProperty("任务编号")
    @JsonProperty("task_id")
    private Long taskId;

    @NotNull(message = "题目分数不能为空")
    @PositiveOrZero(message = "题目分数不能为负数")
    @ApiModelProperty("题目分数")
    @JsonProperty("question_score")
    private Double questionScore;

}
