package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.GraduateRequirementL1;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 毕业要求一级VO
 *
 * @author Rememorio
 */
@ApiModel("毕业要求一级VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class L1VO implements Serializable {

    @ApiModelProperty("毕业一级要求id")
    @JsonProperty("l1_id")
    private Long l1Id;

    @ApiModelProperty("毕业一级要求描述")
    @JsonProperty("l1_desc")
    private String l1Desc;

    @ApiModelProperty("毕业一级要求权重")
    @JsonProperty("l1_weight")
    private Double l1Weight;

    @ApiModelProperty("专业id")
    @JsonProperty("major_id")
    private Long majorId;

    @ApiModelProperty("毕业年份")
    @JsonProperty("graduate_year")
    private Long graduateYear;

    public L1VO(GraduateRequirementL1 l1) {
        this.l1Id = l1.getL1Id();
        this.l1Desc = l1.getL1Desc();
        this.l1Weight = l1.getL1Weight();
        this.majorId = l1.getMajorId();
        this.graduateYear = l1.getGraduateYear();
    }

}
