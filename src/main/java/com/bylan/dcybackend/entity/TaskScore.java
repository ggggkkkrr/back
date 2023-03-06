package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.CreateTaskScoreDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 任务分数
 *
 * @author Rememorio
 */
@ApiModel("任务分数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskScore {

    @ApiModelProperty("学生学号")
    private String studentId;
    @ApiModelProperty("任务id")
    private Long taskId;
    @ApiModelProperty("任务路径")
    private String taskPath;
    @ApiModelProperty("老师评价")
    private String evaluation;
    @ApiModelProperty("系统分析")
    private String systemAnalysis;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    public TaskScore(CreateTaskScoreDTO createTaskScoreDTO) {
        this.studentId = createTaskScoreDTO.getStudentId();
        this.taskId = createTaskScoreDTO.getTaskId();
        this.taskPath = String.join(Constant.Public.SEPARATOR, createTaskScoreDTO.getTaskPath());
    }

}
