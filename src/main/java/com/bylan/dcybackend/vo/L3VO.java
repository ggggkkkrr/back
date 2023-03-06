package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.GraduateRequirementL3;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 毕业三级要求VO
 *
 * @author Rememorio
 */
@ApiModel("毕业三级要求VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class L3VO implements Serializable {

    @ApiModelProperty("毕业三级要求id")
    @JsonProperty("l3_id")
    private Long l3Id;

    @ApiModelProperty("毕业三级要求描述")
    @JsonProperty("l3_desc")
    private String l3Desc;

    @ApiModelProperty("毕业三级要求权重")
    @JsonProperty("l3_weight")
    private Double l3Weight;

    @ApiModelProperty("毕业二级要求id")
    @JsonProperty("l2_weight")
    private Long l2Id;

    public L3VO(GraduateRequirementL3 l3) {
        this.l3Id = l3.getL3Id();
        this.l3Desc = l3.getL3Desc();
        this.l3Weight = l3.getL3Weight();
        this.l2Id = l3.getL2Id();
    }

}

