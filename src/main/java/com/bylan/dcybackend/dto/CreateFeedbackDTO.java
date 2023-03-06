package com.bylan.dcybackend.dto;

import com.bylan.dcybackend.domain.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/21 16:21
 */
@ApiModel("新建反馈DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedbackDTO implements Serializable {

    @NotBlank(message = "教师工号不能为空")
    @ApiModelProperty("教师工号")
    @JsonProperty("teacher_id")
    private String teacherId;

    @NotBlank(message = "学生id不能为空")
    @JsonProperty("student_id")
    @ApiModelProperty("学生id")
    private String studentId;

    @NotBlank(message = "反馈内容不能为空")
    @JsonProperty("feedback_content")
    @ApiModelProperty("反馈内容")
    private String feedbackContent;

    @NotEmpty(message = "反馈内容不能为空")
    @JsonProperty("attachment_path")
    @ApiModelProperty("附件路径列表")
    private List<String> attachmentPath;

    @JsonIgnore
    private Long feedbackStatus;

    @JsonIgnore
    private String filePath;

    public void pathTransfer() {
        if (attachmentPath != null && attachmentPath.size() != 0) {
            filePath = String.join(Constant.Public.SEPARATOR, attachmentPath);
        }
    }

}
