package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生个人达成VO
 *
 * @author wuhuaming
 * @date 2022/4/29 17:18
 */
@ApiModel("学生个人达成VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeStuReachVO implements Serializable {

    @ApiModelProperty("学生姓名")
    @JsonProperty("student_id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty("教学班实际达成度")
    @JsonProperty("actual_reach")
    private Double actualReach;
}
