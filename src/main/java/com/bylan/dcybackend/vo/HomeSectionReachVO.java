package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 首页教学班达成度VO
 *
 * @author Rememorio
 */
@ApiModel("首页教学班达成度VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeSectionReachVO implements Serializable {

    @ApiModelProperty("教学班id")
    @JsonProperty("section_id")
    private Long sectionId;

    @ApiModelProperty("教学班名")
    @JsonProperty("section_name")
    private String sectionName;

    @ApiModelProperty("教学班实际达成度")
    @JsonProperty("actual_reach")
    private Double actualReach;

}
