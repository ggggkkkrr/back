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
 * ILO进展一览VO
 *
 * @author Rememorio
 * @date 2022-05-30 18:45
 */
@ApiModel("ILO进展一览VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloProgressViewVO implements Serializable {

    @ApiModelProperty("预期学习结果id")
    @JsonProperty("ilo_id")
    private Long iloId;

    @ApiModelProperty("预期学习结果编号")
    @JsonProperty("ilo_number")
    private String iloNumber;

    @ApiModelProperty("ILO描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @ApiModelProperty("ILO权重")
    @JsonProperty("ilo_weight")
    private String iloWeight;

    @ApiModelProperty("ILO达成度")
    @JsonProperty("ilo_reach")
    private String iloReach;

    @ApiModelProperty("知识点进展列表")
    @JsonProperty("knowledge_progresses")
    private List<KnowledgeProgressViewVO> knowledgeProgresses;

}
