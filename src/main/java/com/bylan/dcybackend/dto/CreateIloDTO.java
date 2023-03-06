package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 新建ILO的DTO
 *
 * @author Rememorio
 */
@ApiModel("新建ILO的DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIloDTO implements Serializable {

    @NotNull(message = "课程id不能为空")
    @PositiveOrZero(message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotNull(message = "ILO编号不能为空")
    @ApiModelProperty("预期学习结果编号")
    @JsonProperty("ilo_number")
    private String iloNumber;

    @NotNull(message = "ILO描述不能为空")
    @ApiModelProperty("预期学习结果描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @NotNull(message = "初始水平不能为空")
    @ApiModelProperty("初始水平")
    @JsonProperty("initial_level")
    private String initialLevel;

    @NotNull(message = "要求水平不能为空")
    @ApiModelProperty("要求水平")
    @JsonProperty("achieve_level")
    private String achieveLevel;

    @NotNull(message = "ILO权重不能为空")
    @Min(value = 0, message = "ILO权重不能为负数")
    @ApiModelProperty("预期学习结果权重")
    @JsonProperty("ilo_weight")
    private Double iloWeight;

    @NotNull(message = "毕业三级要求id不能为空")
    @PositiveOrZero(message = "毕业三级要求id不能为负数")
    @ApiModelProperty("毕业三级要求id")
    @JsonProperty("l3_id")
    private Long l3Id;

}
