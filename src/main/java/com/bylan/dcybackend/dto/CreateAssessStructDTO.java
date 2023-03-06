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
 * 新建课程考核结构DTO
 *
 * @author Rememorio
 */
@ApiModel("新建课程考核结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssessStructDTO implements Serializable {

    @NotNull(message = "课程id不能为空")
    @PositiveOrZero(message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotNull(message = "课程考核结构权重不能为空")
    @PositiveOrZero(message = "课程考核结构权重不能为负数")
    @ApiModelProperty("课程考核结构权重")
    @JsonProperty("assess_struct_weight")
    private Double assessStructWeight;

    @NotNull(message = "课程考核结构描述不能为空")
    @ApiModelProperty("课程考核结构描述")
    @JsonProperty("assess_struct_desc")
    private String assessStructDesc;

    public Boolean isValid() {
        return assessStructWeight > 0.0;
    }

}
