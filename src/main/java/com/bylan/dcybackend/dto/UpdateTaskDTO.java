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
import java.util.List;

/**
 * 修改任务DTO
 *
 * @author wuhuaming
 */
@ApiModel("修改任务DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDTO implements Serializable {

    @NotNull(message = "任务id不能为空")
    @PositiveOrZero(message = "任务id不能为负数")
    @ApiModelProperty("任务id")
    @JsonProperty("task_id")
    private Long taskId;

    @ApiModelProperty("任务类型")
    @JsonProperty("task_type")
    private Long taskType;

    @ApiModelProperty("任务名称")
    @JsonProperty("task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @JsonProperty("task_desc")
    private String taskDesc;

    @ApiModelProperty("总分")
    @JsonProperty("task_score")
    private Double taskScore;

    @ApiModelProperty("发布时间")
    @JsonProperty("release_time")
    private String releaseTime;

    @ApiModelProperty("截止时间")
    @JsonProperty("deadline")
    private String deadline;

    @ApiModelProperty("教学周")
    @JsonProperty("week")
    private Integer week;

    @ApiModelProperty("任务状态")
    @JsonProperty("task_status")
    private Long taskStatus;

    @ApiModelProperty(value = "附件路径", dataType = "java.util.List")
    @JsonProperty("task_file_path")
    private List<String> taskFilePath;

    public Boolean isValid() {
        if (taskScore != null && taskScore <= 0 || taskFilePath != null && taskFilePath.size() == 0 || week != null && week <= 0) {
            return false;
        }
        return taskType != null || taskName != null || taskDesc != null || taskStatus != null
                || taskScore != null || releaseTime != null || deadline != null || taskFilePath != null || week != null;

    }

}
