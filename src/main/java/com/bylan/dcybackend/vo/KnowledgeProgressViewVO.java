package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 知识点进展一览VO
 *
 * @author Rememorio
 * @date 2022-05-30 18:46
 */
@ApiModel("知识点进展一览VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeProgressViewVO implements Serializable {

    @ApiModelProperty("知识点id")
    @JsonProperty("knowledge_id")
    private Long knowledgeId;

    @ApiModelProperty("知识点对应ilo")
    @JsonProperty("knowledge_number")
    private String knowledgeNumber;

    @ApiModelProperty("知识点描述")
    @JsonProperty("knowledge_desc")
    private String knowledgeDesc;

    @ApiModelProperty("知识点权重")
    @JsonProperty("knowledge_weight")
    private String knowledgeWeight;

    @ApiModelProperty("知识点达成度")
    @JsonProperty("knowledge_reach")
    private String knowledgeReach;

    @ApiModelProperty("考核项目列表")
    @JsonProperty("assess_items")
    private List<AssessItemViewVO> assessItems;

}
