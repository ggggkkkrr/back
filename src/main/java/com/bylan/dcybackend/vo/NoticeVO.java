package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 公告VO
 *
 * @author wuhuaming
 * @date 2022/3/21 15:42
 */
@ApiModel("公告VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO implements Serializable {
    @ApiModelProperty("公告id")
    @JsonProperty("notice_id")
    private Long noticeId;

    @ApiModelProperty("公告内容")
    @JsonProperty("notice_content")
    private String noticeContent;
}
