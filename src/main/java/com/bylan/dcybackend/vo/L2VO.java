package com.bylan.dcybackend.vo;


import com.bylan.dcybackend.entity.GraduateRequirementL2;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 毕业要求二级VO
 *
 * @author Rememorio
 */
@ApiModel("毕业要求二级VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class L2VO implements Serializable {

    @ApiModelProperty("毕业二级要求id")
    @JsonProperty("l2_id")
    private Long l2Id;

    @ApiModelProperty("毕业二级要求描述")
    @JsonProperty("l2_desc")
    private String l2Desc;

    @ApiModelProperty("毕业一级要求id")
    @JsonProperty("l1_id")
    private Long l1Id;

    @ApiModelProperty("毕业二级要求权重")
    @JsonProperty("l2_weight")
    private Double l2Weight;

    public L2VO(GraduateRequirementL2 l2) {
        this.l2Id = l2.getL2Id();
        this.l2Desc = l2.getL2Desc();
        this.l1Id = l2.getL1Id();
        this.l2Weight = l2.getL2Weight();
    }

}
