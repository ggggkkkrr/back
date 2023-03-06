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
 * 任务种类VO
 *
 * @author wuhuaming
 */
@ApiModel("任务种类VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCategoryVO implements Serializable {

    @ApiModelProperty("任务种类")
    @JsonIgnore
    private Long taskType;

    @ApiModelProperty("任务种类描述")
    @JsonProperty("task_type_desc")
    private String taskTypeDesc;

    @ApiModelProperty("任务信息列表")
    @JsonProperty("task_list")
    private List<TaskCategoryItemVO> taskList;

}
