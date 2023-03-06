package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuhuaming
 * @date 2022/5/3 16:32
 */
@ApiModel("任务分数BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskScoreBO {
    private Long taskId;
    private Double score;
}
