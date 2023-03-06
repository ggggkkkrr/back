package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考核项目一览VO
 *
 * @author Rememorio
 * @date 2022-05-30 18:48
 */
@ApiModel("考核项目一览VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessItemViewVO {

    @ApiModelProperty("考核项目id")
    @JsonIgnore
    private Long assessStructId;

    @ApiModelProperty("考核项目描述")
    @JsonProperty("assess_struct_desc")
    private String assessStructDesc;

    @ApiModelProperty("考核项目权重")
    @JsonProperty("assess_struct_weight")
    private String assessStructWeight;

}
