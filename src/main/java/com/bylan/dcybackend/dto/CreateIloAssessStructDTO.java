package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 创建预期学习结果-课程考核结构DTO
 *
 * @author Rememorio
 */
@ApiModel("创建预期学习结果-课程考核结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIloAssessStructDTO implements Serializable {

    @NotNull(message = "ILO的id不能为空")
    @PositiveOrZero(message = "课程考核结构id不能为负数")
    @ApiModelProperty("ilo_id")
    @JsonProperty("ilo_id")
    Long iloId;

    @NotNull(message = "课程考核结构id不能为空")
    @PositiveOrZero(message = "课程考核结构id不能为负数")
    @ApiModelProperty("课程考核结构id")
    @JsonProperty("assess_struct_id")
    Long assessStructId;

    @NotBlank(message = "低于期望不能为空")
    @ApiModelProperty("低于期望")
    @JsonProperty("under_expectation")
    private String underExpectation;

    @NotBlank(message = "符合期望不能为空")
    @ApiModelProperty("符合期望")
    @JsonProperty("meet_expectation")
    private String meetExpectation;

    @NotBlank(message = "符合期望不能为空")
    @ApiModelProperty("超越期望")
    @JsonProperty("beyond_expectation")
    private String beyondExpectation;

}
