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
 * 单个课程所有学生所有题目细则VO
 *
 * @author wuhuaming
 * @date 2022/4/26 16:52
 */
@ApiModel("单个课程所有学生所有题目细则VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuQuestInfoVO implements Serializable {

    @ApiModelProperty("学生id")
    @JsonProperty("student_id")
    private String studentId;

    @ApiModelProperty("学生分数")
    @JsonProperty("course_id")
    private String name;

    @ApiModelProperty("分数细则")
    @JsonProperty("question_detail")
    private List<StuQuestInfoItemVO> stuQuestInfoItemVOList;

}
