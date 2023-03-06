package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 教学班VO
 *
 * @author wuhuaming
 */
@ApiModel("教学班VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionVO implements Serializable {

    @ApiModelProperty("教学班id")
    @JsonProperty("section_id")
    private Long sectionId;

    @ApiModelProperty("教学班名称")
    @JsonProperty("section_name")
    private String sectionName;

}
