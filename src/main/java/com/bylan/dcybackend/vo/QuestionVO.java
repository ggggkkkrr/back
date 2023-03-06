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
 * 题目VO
 *
 * @author wuhuaming
 */
@ApiModel("题目VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO implements Serializable {

    @ApiModelProperty("题目id")
    @JsonProperty("question_id")
    private Long questionId;

    @ApiModelProperty("题目名称")
    @JsonProperty("question_name")
    private String questionName;

    @ApiModelProperty("题目描述")
    @JsonProperty("question_desc")
    private String questionDesc;

    @ApiModelProperty("题目分数")
    @JsonProperty("question_score")
    private Double questionScore;

    @ApiModelProperty("知识点")
    @JsonProperty("knowledge_list")
    private List<KnowlItemVO> knowledgeList;

}
