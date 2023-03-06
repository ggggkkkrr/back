package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 任务种类—任务简要信息VO
 *
 * @author wuhuaming
 */
@ApiModel("任务种类—任务简要信息VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCategoryItemVO implements Serializable {

    @ApiModelProperty("任务id")
    @JsonProperty("task_type")
    private Long taskId;

    @ApiModelProperty("任务名称")
    @JsonProperty("task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @JsonProperty("task_desc")
    private String taskDesc;

    @ApiModelProperty("任务描述")
    @JsonProperty("task_status")
    private String taskStatus;

}
