package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.TeachDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 教学环节细则VO
 *
 * @author Rememorio
 */
@ApiModel("教学环节细则VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachDetailVO implements Serializable {

    @ApiModelProperty("教学环节细则id")
    @JsonProperty("teach_detail_id")
    private Long teachDetailId;

    @ApiModelProperty("教学环节细则权重")
    @JsonProperty("teach_detail_weight")
    private Double teachDetailWeight;

    @ApiModelProperty("教学内容")
    @JsonProperty("teach_content")
    private String teachContent;

    @ApiModelProperty("实现环节")
    @JsonProperty("impl_link")
    private String implLink;

    @ApiModelProperty("教学策略")
    @JsonProperty("teach_strategy")
    private String teachStrategy;

    @ApiModelProperty("毕业二级要求id")
    @JsonProperty("l2_id")
    private Long l2Id;

    @ApiModelProperty("毕业二级要求id描述")
    @JsonProperty("l2_desc")
    private String l2Desc;

    @ApiModelProperty("毕业一级要求id")
    @JsonProperty("l1_id")
    private Long l1Id;

    @ApiModelProperty("毕业一级要求描述")
    @JsonProperty("l1_desc")
    private String l1Desc;

    public TeachDetailVO(TeachDetail teachDetail) {
        this.teachDetailId = teachDetail.getTeachDetailId();
        this.teachDetailWeight = teachDetail.getTeachDetailWeight();
        this.teachContent = teachDetail.getTeachContent();
        this.implLink = teachDetail.getImplLink();
        this.teachStrategy = teachDetail.getTeachStrategy();
        this.l2Id = teachDetail.getL2Id();
    }

}
