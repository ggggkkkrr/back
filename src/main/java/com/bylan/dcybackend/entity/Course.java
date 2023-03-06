package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.dto.UpdateCourseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 某一学期开设的课程
 *
 * @author Rememorio
 */
@ApiModel("某一学期开设的课程")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("课程本身id")
    private Long curriculumId;
    @ApiModelProperty("课程代码")
    private String courseCode;
    @ApiModelProperty("开设学期")
    private Long semester;
    @ApiModelProperty("开设年份")
    private Long year;
    @ApiModelProperty("课程描述")
    private String courseDesc;
    @ApiModelProperty("课程开设状态")
    private Integer courseStatus;
    @ApiModelProperty("先修课程")
    private String prerequisite;
    @ApiModelProperty("教材")
    private String textbook;
    @ApiModelProperty("参考书籍")
    private String refBook;
    @ApiModelProperty("课程网站")
    private String courseWebsite;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public Course(UpdateCourseDTO updateCourseDTO) {
        this.courseId = updateCourseDTO.getCourseId();
        this.curriculumId = updateCourseDTO.getCurriculumId();
        this.courseCode = updateCourseDTO.getCourseCode();
        this.semester = updateCourseDTO.getSemester();
        this.year = updateCourseDTO.getYear();
        this.courseDesc = updateCourseDTO.getCourseDesc();
        this.courseStatus = updateCourseDTO.getCourseStatus();
        this.prerequisite = updateCourseDTO.getPrerequisite();
        this.textbook = updateCourseDTO.getTextbook();
        this.refBook = updateCourseDTO.getRefBook();
        this.courseWebsite = updateCourseDTO.getCourseWebsite();
    }
}
