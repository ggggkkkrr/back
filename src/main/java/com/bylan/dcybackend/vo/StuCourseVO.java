package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生课程VO
 *
 * @author wuhuaming
 * @date 2022/3/28 20:27
 */
@ApiModel("学生课程VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuCourseVO implements Serializable {

    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @ApiModelProperty("教学班id")
    @JsonProperty("section_id")
    private Long sectionId;

}

