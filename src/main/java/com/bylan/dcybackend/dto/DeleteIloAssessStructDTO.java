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
 * 删除预期学习结果-课程考核结构DTO
 *
 * @author Rememorio
 */
@ApiModel("删除预期学习结果-课程考核结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIloAssessStructDTO implements Serializable {

    @NotNull(message = "ILO的id不能为空")
    @PositiveOrZero(message = "课程考核结构id不能为负数")
    @ApiModelProperty("须删除的ilo_id")
    @JsonProperty("ilo_id")
    Long iloId;

    @NotNull(message = "课程考核结构id不能为空")
    @PositiveOrZero(message = "课程考核结构id不能为负数")
    @ApiModelProperty("须删除的课程考核结构id")
    @JsonProperty("assess_struct_id")
    Long assessStructId;

}
