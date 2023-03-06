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

/**
 * 更新学习进度DTO
 *
 * @author Rememorio
 */
@ApiModel("更新学习进度DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseScheduleDTO implements Serializable {

    @NotNull(message = "学习进度id不能为空")
    @PositiveOrZero(message = "学习进度id不能为负数")
    @ApiModelProperty("学习进度id")
    @JsonProperty("schedule_id")
    private Long scheduleId;

    @ApiModelProperty("周次")
    @JsonProperty("week")
    private Long week;

    @ApiModelProperty("教学时数")
    @JsonProperty("teach_hour")
    private Long teachingHour;

    @ApiModelProperty("教学模式")
    @JsonProperty("teach_mode")
    private String teachMode;

    @ApiModelProperty("教学内容")
    @JsonProperty("teach_content")
    private String teachContent;

    @ApiModelProperty("关联的ilo")
    @JsonProperty("ilos")
    private String ilos;

    public Boolean isValid() {
        return week != null || teachingHour != null || teachMode != null || teachContent != null || ilos != null;
    }

}
