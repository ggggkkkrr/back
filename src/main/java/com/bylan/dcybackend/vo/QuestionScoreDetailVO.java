package com.bylan.dcybackend.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 获取学术单个任务所有题目VO
 *
 * @author wuhuaming
 */
@ApiModel("获取学术单个任务所有题目VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionScoreDetailVO implements Serializable {

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

    @ApiModelProperty("个人得分")
    @JsonProperty("personal_score")
    private Double personalScore;

}
