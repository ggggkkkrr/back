package com.bylan.dcybackend.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分数系数VO
 *
 * @author wuhuaming
 */
@ApiModel("分数系数VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDetailVO implements Serializable {

    @ApiModelProperty("分数编号")
    @JsonProperty("question_id")
    private Long questionId;

    @ApiModelProperty("题目名称")
    @JsonProperty("question_name")
    private String questionName;

    @ApiModelProperty("题目分值")
    @JsonProperty("question_score")
    private Double questionScore;

    @ApiModelProperty("实际分数")
    @JsonProperty("actual_score")
    private Double actualScore;

    @ApiModelProperty("状态")
    @JsonProperty("status")
    private String status;
}
