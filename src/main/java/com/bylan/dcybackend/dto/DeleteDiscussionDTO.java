package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/21 10:57
 */
@ApiModel("删除评论DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDiscussionDTO implements Serializable {

    @NotEmpty(message = "discussion_id不能为空")
    @ApiModelProperty(value = "须删除的discussion_id", dataType = "java.util.List")
    @JsonProperty("discussion_ids")
    private List<Long> discussionId;
}
