package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增评论DTO
 *
 * @author wuhuaming
 * @date 2022/3/21 10:10
 */
@ApiModel("新增评论DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiscussionDTO implements Serializable {

    @NotNull(message = "section_id不能为空")
    @Min(value = 0, message = "section_id不能为负数")
    @ApiModelProperty("教学班id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotBlank(message = "user_id不能为空")
    @ApiModelProperty("用户id")
    @JsonProperty("user_id")
    private String userId;

    @NotBlank(message = "user_name不能为空")
    @ApiModelProperty("用户姓名")
    @JsonProperty("user_name")
    private String userName;

    @ApiModelProperty("被评论用户id")
    @JsonProperty("to_user_id")
    private String toUserId;

    @Min(value = 0, message = "to_discussion_id不能为负数")
    @ApiModelProperty("被评论的评论id")
    @JsonProperty("to_discussion_id")
    private Long toDiscussionId;

    @NotBlank(message = "content不能为空")
    @ApiModelProperty("评论内容")
    @JsonProperty("content")
    private String content;

    @Min(value = 0, message = "to_discussion_id不能为负数")
    @ApiModelProperty("一级评论id")
    @JsonProperty("first_discussion_id")
    private Long firstDiscussionId;

    @ApiModelProperty("状态")
    @JsonIgnore
    private Long status;

    public Boolean isValid() {
        // 没有firstDiscussionId 则toUserId和toDiscussionId必须为空
        if (firstDiscussionId == null && (toUserId != null || toDiscussionId != null)) {
            return false;
        }
        // 如果有firstDiscussionId 则toUserId和toDiscussionId必须非空
        return firstDiscussionId == null || (toUserId != null && toDiscussionId != null);
    }
}
