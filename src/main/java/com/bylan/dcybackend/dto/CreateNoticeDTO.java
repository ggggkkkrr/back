package com.bylan.dcybackend.dto;

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
 * @author wuhuaming
 * @date 2022/3/21 15:18
 */
@ApiModel("新建公告DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeDTO implements Serializable {

    @NotNull(message = "教学班id不能为空")
    @Min(value = 0, message = "教学班id不能为负数")
    @ApiModelProperty("教学班id")
    @JsonProperty("section_id")
    private Long sectionId;

    @NotBlank(message = "公告内容不能为空")
    @ApiModelProperty("公告内容")
    @JsonProperty("notice_content")
    private String noticeContent;

}
