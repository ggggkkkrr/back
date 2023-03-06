package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.domain.Constant;
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
 * 学生端FeedbackVO
 *
 * @author wuhuaming
 * @date 2022/3/21 17:26
 */
@ApiModel("学生端FeedbackVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback4StudentVO implements Serializable {

    @ApiModelProperty("反馈id")
    @JsonProperty("feedback_id")
    private Long feedbackId;

    @ApiModelProperty("教师工号")
    @JsonProperty("teacher_id")
    private String teacherId;

    @ApiModelProperty("反馈内容")
    @JsonProperty("feedback_content")
    private String feedbackContent;

    @ApiModelProperty("附件信息")
    @JsonIgnore
    private String filePath;

    @ApiModelProperty("附件路径")
    @JsonProperty("attachment_path")
    private List<String> attachmentPath;

    @ApiModelProperty("反馈状态")
    @JsonProperty("feedback_status")
    private Long feedbackStatus;

    @ApiModelProperty("反馈回复内容")
    @JsonProperty("feedback_response")
    private String feedbackResponse;

    public void pathTransfer() {
        if (filePath != null) {
            attachmentPath = List.of(filePath.split(Constant.Public.SEPARATOR));
        }
    }

}
