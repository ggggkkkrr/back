package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.CourseSchedule;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学习进度VO
 *
 * @author Rememorio
 */
@ApiModel("学习进度VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseScheduleVO implements Serializable {

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

    public CourseScheduleVO(CourseSchedule courseSchedule) {
        this.scheduleId = courseSchedule.getScheduleId();
        this.week = courseSchedule.getWeek();
        this.teachingHour = courseSchedule.getTeachingHour();
        this.teachMode = courseSchedule.getTeachMode();
        this.teachContent = courseSchedule.getTeachContent();
        this.ilos = courseSchedule.getIlos();
    }

}
