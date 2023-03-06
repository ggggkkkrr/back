package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuhuaming
 * @date 2022/5/3 16:08
 */
@ApiModel("学会达成情况BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuReachDetailBO {
    private Long taskId;
    private String studentId;
    private Double taskScore;
}
