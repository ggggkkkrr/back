package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.dto.CreateCourseScheduleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 学习进度
 *
 * @author Rememorio
 */
@ApiModel("学习进度")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSchedule {

    @ApiModelProperty("学习进度id")
    private Long scheduleId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("周次")
    private Long week;
    @ApiModelProperty("教学时数")
    private Long teachingHour;
    @ApiModelProperty("教学模式")
    private String teachMode;
    @ApiModelProperty("教学内容")
    private String teachContent;
    @ApiModelProperty("关联的ilo")
    private String ilos;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public CourseSchedule(CreateCourseScheduleDTO createCourseScheduleDTO) {
        this.courseId = createCourseScheduleDTO.getCourseId();
        this.week = createCourseScheduleDTO.getWeek();
        this.teachingHour = createCourseScheduleDTO.getTeachingHour();
        this.teachMode = createCourseScheduleDTO.getTeachMode();
        this.teachContent = createCourseScheduleDTO.getTeachContent();
        this.ilos = createCourseScheduleDTO.getIlos();
    }

}
