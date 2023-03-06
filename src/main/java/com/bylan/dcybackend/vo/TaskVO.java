package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.entity.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取任务VO
 *
 * @author wuhuaming
 */
@ApiModel("获取任务VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO implements Serializable {

    @ApiModelProperty("任务id")
    @JsonProperty("task_id")
    private Long taskId;

    @ApiModelProperty("教学班id")
    @JsonProperty("section_id")
    private Long sectionId;

    @ApiModelProperty("任务类型")
    @JsonProperty("task_type")
    private Long taskType;

    @ApiModelProperty("任务类型名称")
    @JsonProperty("task_type_name")
    private String taskTypeName;

    @ApiModelProperty("任务名称")
    @JsonProperty("task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @JsonProperty("task_desc")
    private String taskDesc;

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

    @ApiModelProperty(value = "任务文件路径", dataType = "java.util.List")
    @JsonProperty("task_file_path")
    private List<String> taskFilePath;

    public TaskVO(Task task) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.Public.TIME_FORMAT);
        String deadline = sdf.format(task.getDeadline());
        // 发布时间有可能为空
        String releaseTime = null;
        if (task.getReleaseTime() != null) {
            releaseTime = sdf.format(task.getReleaseTime());
        }
        List<String> taskFilePath = new ArrayList<>();
        if (task.getTaskFilePath() != null) {
            taskFilePath = List.of(task.getTaskFilePath().split(Constant.Public.SEPARATOR));
        }
        this.taskId = task.getTaskId();
        this.sectionId = task.getSectionId();
        this.taskType = task.getTaskType();
        this.taskName = task.getTaskName();
        this.taskDesc = task.getTaskDesc();
        this.releaseTime = releaseTime;
        this.deadline = deadline;
        this.week = task.getWeek();
        this.taskStatus = task.getTaskStatus();
        this.taskFilePath = taskFilePath;
    }

}
