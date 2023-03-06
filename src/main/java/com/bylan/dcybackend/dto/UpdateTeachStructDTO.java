package com.bylan.dcybackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;


/**
 * 更新教学环节结构DTO
 *
 * @author Rememorio
 */
@ApiModel("更新教学环节结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeachStructDTO implements Serializable {

    @NotNull(message = "教学环节结构id不能为空")
    @PositiveOrZero(message = "教学环节结构id不能为负数")
    @ApiModelProperty("教学环节结构id")
    @JsonProperty("teach_struct_id")
    private Long teachStructId;

    @ApiModelProperty("教学环节结构描述")
    @JsonProperty("teach_struct_desc")
    private String teachStructDesc;

    @Min(value = 0, message = "课内学时不能为负数")
    @Max(value = 64, message = "课内学时不符合范围")
    @ApiModelProperty("课内学时")
    @JsonProperty("in_class_hour")
    private Long inClassHour;

    @Min(value = 0, message = "课外学时不能为负数")
    @Max(value = 64, message = "课外学时不符合范围")
    @ApiModelProperty("课外学时")
    @JsonProperty("after_class_hour")
    private Long afterClassHour;

    public Boolean isValid() {
        return teachStructDesc != null || inClassHour != null || afterClassHour != null;
    }

}