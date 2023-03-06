package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 创建问题-知识点DTO
 *
 * @author wuhuaming
 */
@ApiModel("创建问题-知识点DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestKnowlPtDTO implements Serializable {

    @ApiModelProperty("问题id")
    @JsonProperty("question_id")
    private Long questionId;

    @ApiModelProperty("知识点id")
    @JsonProperty("knowledge_id")
    private Long knowledgeId;

}
