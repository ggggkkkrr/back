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
 * @author wuhuaming
 * @date 2022/3/21 11:25
 */
@ApiModel("获取评论")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionVO implements Serializable {

    @ApiModelProperty("评论id")
    @JsonProperty("discussion_id")
    private Long discussionId;

    @ApiModelProperty("用户id")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty("用户姓名")
    @JsonProperty("user_name")
    private String userName;

    @ApiModelProperty("被评论用户id")
    @JsonIgnore
    private String toUserId;

    @ApiModelProperty("被评论的评论id")
    @JsonIgnore
    private Long toDiscussionId;

    @ApiModelProperty("评论内容")
    @JsonProperty("content")
    private String content;

    @ApiModelProperty("一级评论id")
    @JsonProperty("first_discussion_id")
    private Long firstDiscussionId;

    @ApiModelProperty("状态")
    @JsonProperty("status")
    private Long status;

    @ApiModelProperty("角色")
    @JsonProperty("role")
    private Long role;

    @ApiModelProperty("回复列表")
    @JsonProperty("reply_discussion_list")
    List<DiscussionVO> replyDiscussion;

}
