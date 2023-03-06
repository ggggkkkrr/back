package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ILO的简单BO
 *
 * @author Rememorio
 */
@ApiModel("ILO的简单BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleIloBO {

    @ApiModelProperty("初始水平")
    private String initialLevel;
    @ApiModelProperty("要求水平")
    private String achieveLevel;
    @ApiModelProperty("预期学习结果描述")
    private String iloDesc;
    @ApiModelProperty("ilo权重")
    private Double iloWeight;

    public SimpleIloBO(IloBO iloBO) {
        this.initialLevel = iloBO.getInitialLevel();
        this.achieveLevel = iloBO.getAchieveLevel();
        this.iloDesc = iloBO.getIloDesc();
        this.iloWeight = iloBO.getIloWeight();
    }
}
