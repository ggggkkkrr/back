package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.Ilo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 预期学习结果VO
 *
 * @author Rememorio
 */
@ApiModel("预期学习结果VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloVO implements Serializable {

    @ApiModelProperty("预期学习结果id")
    @JsonProperty("ilo_id")
    private Long iloId;

    @ApiModelProperty("预期学习结果编号")
    @JsonProperty("ilo_number")
    private String iloNumber;

    @ApiModelProperty("预期学习结果描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @ApiModelProperty("初始水平")
    @JsonProperty("initial_level")
    private String initialLevel;

    @ApiModelProperty("要求水平")
    @JsonProperty("achieve_level")
    private String achieveLevel;

    @ApiModelProperty("预期学习结果权重")
    @JsonProperty("ilo_weight")
    private Double iloWeight;

    @ApiModelProperty("毕业三级要求id")
    @JsonProperty("l3_id")
    private Long l3Id;

    @ApiModelProperty("毕业三级要求描述")
    @JsonProperty("l3_desc")
    private String l3Desc;

    @ApiModelProperty("毕业二级要求id")
    @JsonProperty("l2_id")
    private Long l2Id;

    @ApiModelProperty("毕业二级要求描述")
    @JsonProperty("l2_desc")
    private String l2Desc;

    @ApiModelProperty("毕业一级要求id")
    @JsonProperty("l1_id")
    private Long l1Id;

    @ApiModelProperty("毕业一级要求描述")
    @JsonProperty("l1_desc")
    private String l1Desc;

    public IloVO(Ilo ilo) {
        this.iloId = ilo.getIloId();
        this.iloNumber = ilo.getIloNumber();
        this.iloDesc = ilo.getIloDesc();
        this.initialLevel = ilo.getInitialLevel();
        this.achieveLevel = ilo.getAchieveLevel();
        this.iloWeight = ilo.getIloWeight();
    }

}
