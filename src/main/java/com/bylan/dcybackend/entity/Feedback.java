package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 反馈
 *
 * @author Rememorio
 */
@ApiModel("反馈")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @ApiModelProperty("反馈id")
    private Long feedbackId;
    @ApiModelProperty("教师工号")
    private String teacherId;
    @ApiModelProperty("学生学号")
    private String studentId;
    @ApiModelProperty("反馈内容")
    private String feedbackContent;
    @ApiModelProperty("附件路径")
    private String attachmentPath;
    @ApiModelProperty("反馈状态")
    private Long feedbackStatus;
    @ApiModelProperty("反馈回复内容")
    private String feedbackResponse;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
