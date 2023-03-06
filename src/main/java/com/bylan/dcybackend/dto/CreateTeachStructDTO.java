package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 新建教学环节结构DTO
 *
 * @author Rememorio
 */
@ApiModel("新建教学环节结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeachStructDTO implements Serializable {

    @NotNull(message = "课程id不能为空")
    @PositiveOrZero(message = "课程id不能为负数")
    @ApiModelProperty("课程id")
    @JsonProperty("course_id")
    private Long courseId;

    @NotBlank(message = "教学环节结构描述不能为空")
    @ApiModelProperty("教学环节结构描述")
    @JsonProperty("teach_struct_desc")
    private String teachStructDesc;

    @NotNull(message = "课内学时不能为空")
    @Min(value = 0, message = "课内学时不能为负数")
    @Max(value = 64, message = "课内学时不符合范围")
    @ApiModelProperty("课内学时")
    @JsonProperty("in_class_hour")
    private Long inClassHour;

    @NotNull(message = "课外学时不能为空")
    @Min(value = 0, message = "课外学时不能为负数")
    @Max(value = 64, message = "课外学时不符合范围")
    @ApiModelProperty("课外学时")
    @JsonProperty("after_class_hour")
    private Long afterClassHour;

}
