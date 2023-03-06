package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.Curriculum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程本身列表VO
 *
 * @author Rememorio
 */
@ApiModel("课程本身列表VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumListVO implements Serializable {

    @ApiModelProperty("课程id")
    @JsonProperty("curriculum_id")
    private Long curriculumId;

    @ApiModelProperty("课程代码")
    @JsonProperty("curriculum_code")
    private String curriculumCode;

    @ApiModelProperty("设立年份")
    @JsonProperty("setup_year")
    private Long setupYear;

    @ApiModelProperty("课程名称")
    @JsonProperty("curriculum_name")
    private String curriculumName;

    @ApiModelProperty("课程描述")
    @JsonProperty("curriculum_desc")
    private String curriculumDesc;

    @ApiModelProperty("课程类型")
    @JsonProperty("curriculum_type")
    private Integer curriculumType;

    @ApiModelProperty("专业id")
    @JsonProperty("major_id")
    private Long majorId;

    @ApiModelProperty("学分")
    @JsonProperty("credit")
    private Double credit;

    @ApiModelProperty("学时")
    @JsonProperty("learning_hour")
    private Long learningHour;

    public CurriculumListVO(Curriculum curriculum) {
        this.curriculumId = curriculum.getCurriculumId();
        this.curriculumCode = curriculum.getCurriculumCode();
        this.setupYear = curriculum.getSetupYear();
        this.curriculumName = curriculum.getCurriculumName();
        this.curriculumDesc = curriculum.getCurriculumDesc();
        this.curriculumType = curriculum.getCurriculumType();
        this.majorId = curriculum.getMajorId();
        this.credit = curriculum.getCredit();
        this.learningHour = curriculum.getLearningHour();
    }

}
