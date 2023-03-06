package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

/**
 * 更新题目DTO
 *
 * @author wuhuaming
 */
@ApiModel("更新题目DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuestionDTO implements Serializable {

    @NotNull(message = "题目id不能为空")
    @PositiveOrZero(message = "题目id不能为负数")
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

    @ApiModelProperty(value = "知识点", dataType = "java.util.List")
    @JsonProperty("knowledge_id")
    private List<Long> knowlId;

    public Boolean isEmpty() {
        return questionId == null || questionName == null && questionScore == null && questionDesc == null;
    }

    public Boolean isValid() {
        if (knowlId != null && knowlId.size() == 0) {
            return false;
        }
        return questionName != null || questionDesc != null || questionScore != null || knowlId != null;
    }

}
