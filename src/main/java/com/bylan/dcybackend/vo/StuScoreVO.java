package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生得分VO
 *
 * @author wuhuaming
 * @date 2022/5/3 15:37
 */
@ApiModel("学生得分VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuScoreVO implements Serializable {

    @ApiModelProperty("任务id")
    @JsonProperty("task_id")
    private Long taskId;

    @ApiModelProperty("得分")
    @JsonProperty("score")
    private Double score;

}
