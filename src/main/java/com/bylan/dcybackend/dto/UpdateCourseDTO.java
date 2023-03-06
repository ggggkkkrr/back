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
 * 更新课程DTO
 *
 * @author Rememorio
 */
@ApiModel("更新课程DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseDTO implements Serializable {

    @NotNull(message = "课程id不能为空")
    @PositiveOrZero(message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @ApiModelProperty("课程本身id")
    @JsonProperty("curriculum_id")
    private Long curriculumId;

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

    @ApiModelProperty("课程开设状态")
    @JsonProperty("course_status")
    private Integer courseStatus;

    @ApiModelProperty("先修课程")
    @JsonProperty("prerequisite")
    private String prerequisite;

    @ApiModelProperty("教材")
    @JsonProperty("textbook")
    private String textbook;

    @ApiModelProperty("参考书籍")
    @JsonProperty("ref_book")
    private String refBook;

    @ApiModelProperty("课程网站")
    @JsonProperty("course_website")
    private String courseWebsite;

    public Boolean isValid() {
        return courseId != null && (curriculumId != null && curriculumId < 0 || courseCode != null
                || semester != null || year != null || courseDesc != null || courseStatus != null
                || prerequisite != null || textbook != null || refBook != null || courseWebsite != null);
    }

}
