package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.dto.CreatePracticeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 实践
 *
 * @author Rememorio
 */
@ApiModel("实践")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Practice {

    @ApiModelProperty("实践id")
    private Long practiceId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("实验编号")
    private String practiceNumber;
    @ApiModelProperty("实验描述")
    private String practiceDesc;
    @ApiModelProperty("课时")
    private Long classHour;
    @ApiModelProperty("活动内容与目标")
    private String contentTarget;
    @ApiModelProperty("活动任务")
    private String task;
    @ApiModelProperty("考核形式")
    private String assessmentForm;
    @ApiModelProperty("评分标准")
    private String scoreStandard;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public Practice(CreatePracticeDTO createPracticeDTO) {
        this.courseId = createPracticeDTO.getCourseId();
        this.practiceNumber = createPracticeDTO.getPracticeNumber();
        this.practiceDesc = createPracticeDTO.getPracticeDesc();
        this.classHour = createPracticeDTO.getClassHour();
        this.contentTarget = createPracticeDTO.getContentTarget();
        this.task = createPracticeDTO.getTask();
        this.assessmentForm = createPracticeDTO.getAssessmentForm();
        this.scoreStandard = createPracticeDTO.getScoreStandard();
        this.remark = createPracticeDTO.getRemark();
    }

}
