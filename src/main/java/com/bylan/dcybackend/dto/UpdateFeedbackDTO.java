package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author wuhuaming
 * @date 2022/3/21 16:51
 */
@ApiModel("教师答复反馈DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFeedbackDTO {

    @NotNull(message = "反馈id不能为空")
    @PositiveOrZero(message = "反馈id不能为负数")
    @JsonProperty("feedback_id")
    @ApiModelProperty("反馈id")
    private String feedbackId;

    @NotBlank(message = "反馈回复内容不能为空")
    @JsonProperty("feedback_response")
    @ApiModelProperty("反馈回复内容")
    private String feedbackResponse;

    @JsonIgnore
    private Long feedbackStatus;
}
