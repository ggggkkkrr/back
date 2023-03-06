package com.bylan.dcybackend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel("讨论区评论")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discussion {

    @ApiModelProperty("评论id")
    private Long discussionId;
    @ApiModelProperty("教学班id")
    private Long courseId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("被评论用户id")
    private String toUserId;
    @ApiModelProperty("被评论的评论id")
    private Long toDiscussionId;
    @ApiModelProperty("评论内容")
    private String content;
    @ApiModelProperty("一级评论id")
    private Long firstDiscussionId;
    @ApiModelProperty("状态")
    private Long status;

}
