package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 班级教学周达成细则-具体数据VO
 *
 * @author wuhuaming
 * @date 2022/5/3 15:33
 */
@ApiModel("班级教学周达成细则-具体数据VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuReachDetailDataVO implements Serializable {

    @ApiModelProperty("学生id")
    @JsonProperty("student_id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    @JsonProperty("student_name")
    private String studentName;

    @ApiModelProperty("学生分数细则")
    @JsonProperty("transcript")
    private List<StuScoreVO> stuScoreVOList;

}
