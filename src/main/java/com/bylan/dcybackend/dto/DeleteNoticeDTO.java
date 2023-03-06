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
 * 删除公告DTO
 *
 * @author wuhuaming
 * @date 2022/3/21 15:35
 */
@ApiModel("删除公告DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteNoticeDTO implements Serializable {

    @NotEmpty(message = "notice_id不能为空")
    @ApiModelProperty(value = "须删除的notice_id", dataType = "java.util.List")
    @JsonProperty("notice_id")
    private List<Long> noticeId;

}