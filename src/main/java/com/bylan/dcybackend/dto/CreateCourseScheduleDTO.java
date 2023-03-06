package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 新建学习进度DTO
 *
 * @author Rememorio
 */
@ApiModel("新建学习进度DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseScheduleDTO implements Serializable {

    @NotNull(message = "课程id不能为空")
    @PositiveOrZero(message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotNull(message = "周次不能为空")
    @PositiveOrZero(message = "周次不符合范围")
    @ApiModelProperty("周次")
    @JsonProperty("week")
    private Long week;

    @NotNull(message = "教学时长不能为空")
    @Positive(message = "教学时长不符合范围")
    @ApiModelProperty("教学时数")
    @JsonProperty("teach_hour")
    private Long teachingHour;

    @NotBlank(message = "教学形式不能为空")
    @ApiModelProperty("教学模式")
    @JsonProperty("teach_mode")
    private String teachMode;

    @NotBlank(message = "教学内容不能为空")
    @ApiModelProperty("教学内容")
    @JsonProperty("teach_content")
    private String teachContent;

    @NotBlank(message = "关联的ilo不能为空")
    @ApiModelProperty("关联的ilo")
    @JsonProperty("ilos")
    private String ilos;

}
