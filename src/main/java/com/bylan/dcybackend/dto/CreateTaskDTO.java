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
import java.util.List;

/**
 * 新建任务DTO
 *
 * @author wuhuaming
 */
@ApiModel("新建任务DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO implements Serializable {

    @NotNull(message = "教学班不能为空")
    @PositiveOrZero(message = "教学班id不能为负数")
    @ApiModelProperty("教学班id")
    @JsonProperty("section_id")
    private Long sectionId;

    @NotNull(message = "任务类型不能为空")
    @ApiModelProperty("任务类型")
    @JsonProperty("task_type")
    private Long taskType;

    @NotBlank(message = "任务名称不能为空")
    @ApiModelProperty("任务名称")
    @JsonProperty("task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @JsonProperty("task_desc")
    private String taskDesc;


    @NotBlank(message = "截止时间不能为空")
    @ApiModelProperty("截止时间")
    @JsonProperty("deadline")
    private String deadline;

    @NotNull(message = "教学周不能为空")
    @PositiveOrZero(message = "教学周不能为负数")
    @ApiModelProperty("教学周")
    @JsonProperty("week")
    private Integer week;


    @ApiModelProperty(value = "附件路径", dataType = "java.util.List")
    @JsonProperty("task_file_path")
    private List<String> taskFilePath;

}
