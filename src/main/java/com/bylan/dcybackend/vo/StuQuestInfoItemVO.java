package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生单个题目分数VO
 *
 * @author wuhuaming
 * @date 2022/4/26 16:57
 */
@ApiModel("学生单个题目分数VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuQuestInfoItemVO implements Serializable {
    @ApiModelProperty("题目分数")
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
}
