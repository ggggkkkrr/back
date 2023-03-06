package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 校验题目分数BO
 *
 * @author Rememorio
 * @date 2022-06-21 14:53
 */
@ApiModel("校验题目分数BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckQuestScoreBO {

    @ApiModelProperty("结果")
    private Boolean result;
    @ApiModelProperty("消息")
    private String message;

}
