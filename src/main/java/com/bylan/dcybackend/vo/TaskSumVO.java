package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 任务分数统计VO
 *
 * @author wuhuaming
 */
@ApiModel("任务分数统计VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSumVO implements Serializable {

    @ApiModelProperty("学号")
    @JsonProperty("student_id")
    private String studentId;

    @ApiModelProperty("姓名")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty("任务状态")
    @JsonProperty("status")
    private String status;

    @ApiModelProperty("附件")
    @JsonProperty("path")
    private List<String> path;

    @ApiModelProperty("文档")
    @JsonIgnore
    private String filePath;

    @ApiModelProperty("各题分数")
    @JsonIgnore
    private Double[] scoreDetail;

    @ApiModelProperty("总分")
    @JsonProperty("score")
    private Double score;

    @ApiModelProperty("教师评价")
    @JsonProperty("evaluation")
    private String evaluation;

}
