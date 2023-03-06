package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

/**
 * 创建任务得分DTO
 *
 * @author wuhuaming
 */
@ApiModel("创建任务得分DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskScoreDTO implements Serializable {

    @NotBlank(message = "学生学号不能为空")
    @ApiModelProperty("学生学号")
    @JsonProperty("student_id")
    private String studentId;

    @NotNull(message = "任务不能为空")
    @PositiveOrZero(message = "任务id不能为负数")
    @ApiModelProperty("任务id")
    @JsonProperty("task_id")
    private Long taskId;

    @NotEmpty(message = "任务路径不能为空")
    @ApiModelProperty(value = "任务路径", dataType = "java.util.List")
    @JsonProperty("task_path")
    private List<String> taskPath;

}
