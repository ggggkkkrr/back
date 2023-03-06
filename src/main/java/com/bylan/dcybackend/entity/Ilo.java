package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.bo.IloBO;
import com.bylan.dcybackend.dto.CreateIloDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 预期学习结果
 *
 * @author Rememorio
 */
@ApiModel("预期学习结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ilo {

    @ApiModelProperty("预期学习结果id")
    private Long iloId;
    @ApiModelProperty("课程id")
    private Long courseId;
    @ApiModelProperty("预期学习结果编号")
    private String iloNumber;
    @ApiModelProperty("预期学习结果描述")
    private String iloDesc;
    @ApiModelProperty("初始水平")
    private String initialLevel;
    @ApiModelProperty("要求水平")
    private String achieveLevel;
    @ApiModelProperty("预期学习结果权重")
    private Double iloWeight;
    @ApiModelProperty("毕业三级要求id")
    private Long l3Id;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public Ilo(IloBO iloBO) {
        // 提取iloDesc中的iloNumber
        iloBO.setIloNumberByDesc();
        this.courseId = iloBO.getCourseId();
        this.achieveLevel = iloBO.getAchieveLevel();
        this.initialLevel = iloBO.getInitialLevel();
        this.iloDesc = iloBO.getIloDesc();
        this.iloNumber = iloBO.getIloNumber();
        this.iloWeight = iloBO.getIloWeight();
    }

    public Ilo(Long courseId, String iloNumber) {
        this.courseId = courseId;
        this.iloNumber = iloNumber;
    }

    public Ilo(CreateIloDTO createIloDTO) {
        this.courseId = createIloDTO.getCourseId();
        this.iloNumber = createIloDTO.getIloNumber();
        this.iloDesc = createIloDTO.getIloDesc();
        this.iloWeight = createIloDTO.getIloWeight();
        this.initialLevel = createIloDTO.getInitialLevel();
        this.achieveLevel = createIloDTO.getAchieveLevel();
        this.l3Id = createIloDTO.getL3Id();
    }
}
