package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.bo.IloAssessStructBO;
import com.bylan.dcybackend.dto.CreateIloAssessStructDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 预期学习结果-课程考核结构
 *
 * @author Rememorio
 */
@ApiModel("预期学习结果-课程考核结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloAssessStruct {

    @ApiModelProperty("预期学习结果id")
    private Long iloId;
    @ApiModelProperty("课程考核结构id")
    private Long assessStructId;
    @ApiModelProperty("低于期望")
    private String underExpectation;
    @ApiModelProperty("符合期望")
    private String meetExpectation;
    @ApiModelProperty("超越期望")
    private String beyondExpectation;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public IloAssessStruct(IloAssessStructBO iloAssessStructBO) {
        this.underExpectation = iloAssessStructBO.getUnderExpectation();
        this.meetExpectation = iloAssessStructBO.getMeetExpectation();
        this.beyondExpectation = iloAssessStructBO.getBeyondExpectation();
    }

    public IloAssessStruct(CreateIloAssessStructDTO createIloAssessStructDTO) {
        this.iloId = createIloAssessStructDTO.getIloId();
        this.assessStructId = createIloAssessStructDTO.getAssessStructId();
        this.underExpectation = createIloAssessStructDTO.getUnderExpectation();
        this.meetExpectation = createIloAssessStructDTO.getMeetExpectation();
        this.beyondExpectation = createIloAssessStructDTO.getBeyondExpectation();
    }

}
