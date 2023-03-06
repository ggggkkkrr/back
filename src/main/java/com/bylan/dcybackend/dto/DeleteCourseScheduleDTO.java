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
 * 删除学习进度DTO
 *
 * @author Rememorio
 */
@ApiModel("删除学习进度DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCourseScheduleDTO implements Serializable {

    @NotEmpty(message = "学习进度id不能为空")
    @ApiModelProperty("须删除的学习进度id")
    @JsonProperty("schedule_ids")
    List<Long> scheduleIds;

}