package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

/**
 * 新建题目DTO
 *
 * @author wuhuaming
 */
@ApiModel("新建题目DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionDTO implements Serializable {

    @ApiModelProperty("题目id 用于回填")
    @JsonIgnore
    private Long questionId;

    @NotNull(message = "任务id不能为空")
    @PositiveOrZero(message = "任务id不能为负数")
    @ApiModelProperty("任务id")
    @JsonProperty("task_id")
    private Long taskId;

    @NotNull(message = "题目昵称不能为空")
    @ApiModelProperty("题目名称")
    @JsonProperty("question_name")
    private String questionName;

    @NotNull(message = "题目描述不能为空")
    @ApiModelProperty("题目描述")
    @JsonProperty("question_desc")
    private String questionDesc;

    @NotNull(message = "题目分数不能为空")
    @PositiveOrZero(message = "题目分数不能为负数")
    @ApiModelProperty("题目分数")
    @JsonProperty("question_score")
    private Double questionScore;

    @NotEmpty(message = "知识点不能为空")
    @ApiModelProperty(value = "知识点", dataType = "java.util.List")
    @JsonProperty("knowledge_id")
    private List<Long> konwlId;

}
