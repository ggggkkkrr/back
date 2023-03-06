package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.AssessStruct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程考核结构VO
 *
 * @author Rememorio
 */
@ApiModel("课程考核结构VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessStructVO implements Serializable {

    @ApiModelProperty("课程考核结构id")
    @JsonProperty("assess_struct_id")
    private Long assessStructId;

    @ApiModelProperty("课程考核结构权重")
    @JsonProperty("assess_struct_weight")
    private Double assessStructWeight;

    @ApiModelProperty("课程考核结构描述")
    @JsonProperty("assess_struct_desc")
    private String assessStructDesc;

    public AssessStructVO(AssessStruct assessStruct) {
        this.assessStructId = assessStruct.getAssessStructId();
        this.assessStructWeight = assessStruct.getAssessStructWeight();
        this.assessStructDesc = assessStruct.getAssessStructDesc();
    }

}
