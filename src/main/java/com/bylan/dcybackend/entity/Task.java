package com.bylan.dcybackend.entity;


import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.CreateTaskDTO;
import com.bylan.dcybackend.dto.UpdateTaskDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务
 *
 * @author Rememorio
 */
@ApiModel("任务")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @ApiModelProperty("任务id")
    private Long taskId;
    @ApiModelProperty("教学班id")
    private Long sectionId;
    @ApiModelProperty("任务类型")
    private Long taskType;
    @ApiModelProperty("任务名称")
    private String taskName;
    @ApiModelProperty("任务描述")
    private String taskDesc;
    @ApiModelProperty("发布时间")
    private Date releaseTime;
    @ApiModelProperty("截止时间")
    private Date deadline;
    @ApiModelProperty("教学周")
    private Integer week;
    @ApiModelProperty("任务状态")
    private Long taskStatus;
    @ApiModelProperty("任务文件路径")
    private String taskFilePath;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

    // 少主键和modify_time

    public Task(CreateTaskDTO createTaskDTO) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.Public.TIME_FORMAT);
        Date deadlineDate;
        try {
            deadlineDate = sdf.parse(createTaskDTO.getDeadline());
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
        this.sectionId = createTaskDTO.getSectionId();
        this.taskType = createTaskDTO.getTaskType();
        this.taskName = createTaskDTO.getTaskName();
        this.taskDesc = createTaskDTO.getTaskDesc();
        this.deadline = deadlineDate;
        this.week = createTaskDTO.getWeek();
        this.taskFilePath = String.join(Constant.Public.SEPARATOR, createTaskDTO.getTaskFilePath());
    }

    // 少section_id、create_time和modify_time

    public Task(UpdateTaskDTO updateTaskDTO) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.Public.TIME_FORMAT);
        Date releaseTimeDate = null;
        Date deadlineDate = null;
        try {
            if (updateTaskDTO.getReleaseTime() != null) {
                releaseTimeDate = sdf.parse(updateTaskDTO.getReleaseTime());
            }
            if (updateTaskDTO.getDeadline() != null) {
                deadlineDate = sdf.parse(updateTaskDTO.getDeadline());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
        String taskFilePath = null;
        if (updateTaskDTO.getTaskFilePath() != null) {
            taskFilePath = String.join(Constant.Public.SEPARATOR, updateTaskDTO.getTaskFilePath());
        }

        this.taskId = updateTaskDTO.getTaskId();
        this.taskType = updateTaskDTO.getTaskType();
        this.taskName = updateTaskDTO.getTaskName();
        this.taskDesc = updateTaskDTO.getTaskDesc();
        this.releaseTime = releaseTimeDate;
        this.deadline = deadlineDate;
        this.week = updateTaskDTO.getWeek();
        this.taskFilePath = taskFilePath;
    }

}
