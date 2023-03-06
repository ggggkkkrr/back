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
 * 发布任务DTO
 *
 * @author wuhuaming
 */
@ApiModel("发布任务DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishTaskDTO implements Serializable {

    @NotEmpty(message = "任务编号不能为空")
    @ApiModelProperty("任务编号")
    @JsonProperty("task_ids")
    private List<Long> taskIds;

}
