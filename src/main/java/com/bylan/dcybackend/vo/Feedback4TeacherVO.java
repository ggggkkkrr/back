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
 * 教师端FeedbackVO
 *
 * @author wuhuaming
 * @date 2022/3/21 17:09
 */
@ApiModel("教师端FeedbackVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback4TeacherVO implements Serializable {

    @ApiModelProperty("反馈id")
    @JsonProperty("feedback_id")
    private Long feedbackId;

    @ApiModelProperty("学生学号")
    @JsonProperty("student_id")
    private String studentId;

    @ApiModelProperty("反馈内容")
    @JsonProperty("feedback_content")
    private String feedbackContent;

    @ApiModelProperty("附件信息")
    @JsonIgnore
    private String filePath;

    @ApiModelProperty("附件路径")
    @JsonProperty("attachment_path")
    private List<String> attachmentPath;

    public void pathTransfer() {
        if (filePath != null) {
            attachmentPath = List.of(filePath.split(Constant.Public.SEPARATOR));
        }
    }

}
