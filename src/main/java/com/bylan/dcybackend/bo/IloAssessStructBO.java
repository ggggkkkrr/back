package com.bylan.dcybackend.bo;

import com.bylan.dcybackend.entity.IloAssessStruct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ILO-课程考核结构BO
 *
 * @author Rememorio
 */
@ApiModel("ILO-课程考核结构BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloAssessStructBO {

    @ApiModelProperty("预期学习结果编号")
    private String iloNumber;
    @ApiModelProperty("低于期望")
    private String underExpectation;
    @ApiModelProperty("符合期望")
    private String meetExpectation;
    @ApiModelProperty("超越期望")
    private String beyondExpectation;
    @ApiModelProperty("课程考核结构描述")
    private String assessStructDesc;
    @ApiModelProperty("课程id")
    private Long courseId;

    public IloAssessStructBO(IloAssessStruct iloAssessStruct) {
        this.underExpectation = iloAssessStruct.getUnderExpectation();
        this.meetExpectation = iloAssessStruct.getMeetExpectation();
        this.beyondExpectation = iloAssessStruct.getBeyondExpectation();
    }

}
