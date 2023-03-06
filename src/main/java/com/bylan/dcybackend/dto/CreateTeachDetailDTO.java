package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * 新建教学环节细则DTO
 *
 * @author Rememorio
 */
@ApiModel("新建教学环节细则DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeachDetailDTO {

    @NotNull(message = "课程id不能为空")
    @PositiveOrZero(message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotNull(message = "教学环节细则权重不能为空")
    @PositiveOrZero(message = "教学环节细则权重不能为负数")
    @ApiModelProperty("教学环节细则权重")
    @JsonProperty("teach_detail_weight")
    private Double teachDetailWeight;

    @NotNull(message = "教学内容不能为空")
    @ApiModelProperty("教学内容")
    @JsonProperty("teach_content")
    private String teachContent;

    @NotNull(message = "实现环节不能为空")
    @ApiModelProperty("实现环节")
    @JsonProperty("impl_link")
    private String implLink;

    @NotNull(message = "教学策略不能为空")
    @ApiModelProperty("教学策略")
    @JsonProperty("teach_strategy")
    private String teachStrategy;

    @NotNull(message = "毕业二级要求id不能为空")
    @PositiveOrZero(message = "毕业二级要求id不能为负数")
    @ApiModelProperty("毕业二级要求id")
    @JsonProperty("l2_id")
    private Long l2Id;

}
