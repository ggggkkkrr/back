package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程列表VO
 *
 * @author Rememorio
 */
@ApiModel("课程VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseListVO implements Serializable {

    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @ApiModelProperty("课程代码")
    @JsonProperty("course_code")
    private String courseCode;

    @ApiModelProperty("开设学期")
    @JsonProperty("semester")
    private Long semester;

    @ApiModelProperty("开设年份")
    @JsonProperty("year")
    private Long year;

    @ApiModelProperty("课程描述")
    @JsonProperty("course_desc")
    private String courseDesc;

    @ApiModelProperty("教材")
    @JsonProperty("textbook")
    private String textbook;

    @ApiModelProperty("参考书籍")
    @JsonProperty("ref_book")
    private String refBook;

    @ApiModelProperty("课程网站")
    @JsonProperty("course_website")
    private String courseWebsite;

    public CourseListVO(Course course) {
        this.courseId = course.getCourseId();
        this.courseCode = course.getCourseCode();
        this.semester = course.getSemester();
        this.year = course.getYear();
        this.courseDesc = course.getCourseDesc();
        this.textbook = course.getTextbook();
        this.refBook = course.getRefBook();
        this.courseWebsite = course.getCourseWebsite();
    }

}
