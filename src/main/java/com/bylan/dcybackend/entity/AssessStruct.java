package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.dto.CreateAssessStructDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程考核结构
 *
 * @author Rememorio
 */
@ApiModel("课程考核结构")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessStruct {

    @ApiModelProperty("课程考核结构id")
    private Long assessStructId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("课程考核结构权重")
    private Double assessStructWeight;
    @ApiModelProperty("课程考核结构描述")
    private String assessStructDesc;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public AssessStruct(Long courseId, Double assessStructWeight, String assessStructDesc) {
        this.courseId = courseId;
        this.assessStructWeight = assessStructWeight;
        this.assessStructDesc = assessStructDesc;
    }

    public AssessStruct(Long courseId, String assessStructDesc) {
        this.courseId = courseId;
        this.assessStructDesc = assessStructDesc;
    }

    public AssessStruct(CreateAssessStructDTO createAssessStructDTO) {
        this.courseId = createAssessStructDTO.getCourseId();
        this.assessStructWeight = createAssessStructDTO.getAssessStructWeight();
        this.assessStructDesc = createAssessStructDTO.getAssessStructDesc();
    }

}
