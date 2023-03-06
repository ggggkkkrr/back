package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课程进展一览VO
 *
 * @author Rememorio
 * @date 2022-05-06 16:16
 */
@ApiModel("课程进展一览VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressViewVO implements Serializable {

    @ApiModelProperty("课程id")
    @JsonIgnore
    private Long courseId;

    @ApiModelProperty("课程达成度")
    @JsonProperty("course_reach")
    private String courseReach;

    @ApiModelProperty("ILO进展列表")
    @JsonProperty("ilo_progresses")
    private List<IloProgressViewVO> iloProgresses;

}
