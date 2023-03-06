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
 * 知识点列表VO
 *
 * @author wuhuaming
 */
@ApiModel("知识点列表VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowlListVO implements Serializable {

    @ApiModelProperty("预期学习结果id")
    @JsonProperty("ilo_id")
    private Long iloId;

    @ApiModelProperty("预期学习结果编号")
    @JsonProperty("ilo_number")
    private String iloNumber;

    @ApiModelProperty("预期学习结果描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @ApiModelProperty("预期学习对应知识点")
    @JsonProperty("knowledge_list")
    private List<KnowlItemVO> knowlItemVOList;

}
