package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 删除任务DTO
 *
 * @author wuhuaming
 */
@ApiModel("删除任务DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTaskDTO implements Serializable {

    @NotEmpty(message = "任务id不能为空")
    @ApiModelProperty("须删除的任务id")
    @JsonProperty("task_ids")
    List<Long> taskIds;

}
