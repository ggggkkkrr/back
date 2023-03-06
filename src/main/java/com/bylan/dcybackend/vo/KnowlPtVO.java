package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 知识点VO
 *
 * @author wuhuaming
 */
@ApiModel("知识点VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowlPtVO implements Serializable {

    @ApiModelProperty("知识点id")
    @JsonProperty("knowledge_id")
    private Long knowlId;

    @ApiModelProperty("知识点对应ilo")
    @JsonProperty("knowledge_num")
    private String knowlNum;

    @ApiModelProperty("知识点描述")
    @JsonProperty("knowledge_desc")
    private String knowlDesc;

}
