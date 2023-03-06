package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 删除问题DTO
 *
 * @author wuhuaming
 */
@ApiModel("删除问题DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteQuestionDTO implements Serializable {

    @NotEmpty(message = "题目id不能为空")
    @ApiModelProperty(value = "预删除的题目id", dataType = "java.util.List")
    @JsonProperty("question_ids")
    List<Long> questionIds;

}
