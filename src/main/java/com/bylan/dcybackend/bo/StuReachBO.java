package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuhuaming
 * @date 2022/4/29 11:13
 */
@ApiModel("达成度计算BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuReachBO {

    @ApiModelProperty("达成度")
    private Double score;

    @ApiModelProperty("学生编号")
    private Double studentId;

    public StuReachBO(double score) {
        this.score = score;
    }

    public Boolean isValid() {
        return score >= 0.0;
    }

    public static StuReachBO invalidStuReachBO() {
        return new StuReachBO(-1.0d);
    }
}
