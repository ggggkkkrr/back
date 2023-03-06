package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 知识点项VO
 *
 * @author wuhuaming
 */
@ApiModel("知识点项VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowlItemVO implements Serializable {

    @ApiModelProperty("知识点id")
    @JsonProperty("knowledge_id")
    private Long knowledgeId;

    @ApiModelProperty("知识点编号")
    @JsonProperty("knowledge_number")
    private String knowledgeNumber;

    @ApiModelProperty("知识点描述")
    @JsonProperty("knowledge_desc")
    private String knowledgeDesc;

}
