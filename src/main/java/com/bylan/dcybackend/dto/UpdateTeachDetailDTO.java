package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 更新教学环节细则DTO
 *
 * @author Rememorio
 */
@ApiModel("更新教学环节细则DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeachDetailDTO implements Serializable {

    @NotNull(message = "教学环节细则id不能为空")
    @PositiveOrZero(message = "教学环节细则id不能为负数")
    @ApiModelProperty("教学环节细则id")
    @JsonProperty("teach_detail_id")
    private Long teachDetailId;

    @ApiModelProperty("教学环节细则权重")
    @JsonProperty("teach_detail_wight")
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

    public Boolean isValid() {
        return teachDetailWeight != null || teachContent != null || implLink != null || teachStrategy != null;
    }

}
