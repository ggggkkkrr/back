package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.IloAssessStruct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 预期学习结果-课程考核结构VO
 *
 * @author Rememorio
 */
@ApiModel("预期学习结果-课程考核结构VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloAssessStructVO implements Serializable {

    @ApiModelProperty("预期学习结果id")
    @JsonProperty("ilo_id")
    private Long iloId;

    @ApiModelProperty("预期学习结果编号")
    @JsonProperty("ilo_number")
    private String iloNumber;

    @ApiModelProperty("预期学习结果描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @ApiModelProperty("课程考核结构id")
    @JsonProperty("assess_struct_id")
    private Long assessStructId;

    @ApiModelProperty("课程考核结构权重")
    @JsonProperty("assess_struct_weight")
    private Double assessStructWeight;

    @ApiModelProperty("课程考核结构描述")
    @JsonProperty("assess_struct_desc")
    private String assessStructDesc;

    @ApiModelProperty("低于期望")
    @JsonProperty("under_expectation")
    private String underExpectation;

    @ApiModelProperty("符合期望")
    @JsonProperty("meet_expectation")
    private String meetExpectation;

    @ApiModelProperty("超越期望")
    @JsonProperty("beyond_expectation")
    private String beyondExpectation;

    public IloAssessStructVO(IloAssessStruct iloAssessStruct) {
        this.iloId = iloAssessStruct.getIloId();
        this.assessStructId = iloAssessStruct.getAssessStructId();
        this.underExpectation = iloAssessStruct.getUnderExpectation();
        this.meetExpectation = iloAssessStruct.getMeetExpectation();
        this.beyondExpectation = iloAssessStruct.getBeyondExpectation();
    }

}
