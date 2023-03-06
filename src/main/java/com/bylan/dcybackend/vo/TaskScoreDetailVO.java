package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/4/4 15:06
 */
@ApiModel("获取学生任务")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskScoreDetailVO implements Serializable {

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

    @ApiModelProperty("教学周")
    @JsonProperty("week")
    private Integer week;

    @ApiModelProperty("任务文件路径")
    @JsonProperty("task_file_path")
    private List<String> taskFilePath;

    @ApiModelProperty("老师评价")
    @JsonProperty("evaluation")
    private String evaluation;

    @ApiModelProperty("系统分析")
    @JsonProperty("system_analysis")
    private String systemAnalysis;

    @ApiModelProperty("总分")
    @JsonProperty("score")
    private Double score;

    @ApiModelProperty("任务文件路径待拼接")
    @JsonIgnore
    private String path;

    @ApiModelProperty("任务状态")
    @JsonProperty("task_status")
    private Integer taskStatus;

}