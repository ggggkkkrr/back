package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 首页教学班达成度VO
 *
 * @author Rememorio
 */
@ApiModel("首页教学班达成度VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeCourseReachVO implements Serializable {

    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @ApiModelProperty("课程名称")
    @JsonProperty("course_name")
    private String curriculumName;

    @ApiModelProperty("课程实际达成度")
    @JsonProperty("actual_reach")
    private Double actualReach;

    @ApiModelProperty("课程预期达成度")
    @JsonProperty("expected_reach")
    private Double expectedReach;

    @ApiModelProperty("教学班达成度列表")
    @JsonProperty("section_reaches")
    private List<HomeSectionReachVO> sectionReachVOList;

}
