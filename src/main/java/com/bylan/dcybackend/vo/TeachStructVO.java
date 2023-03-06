package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.entity.TeachStruct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教学环节结构VO
 *
 * @author Rememorio
 */
@ApiModel("教学环节结构VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachStructVO {

    @ApiModelProperty("教学环节结构id")
    @JsonProperty("teach_struct_id")
    private Long teachStructId;

    @ApiModelProperty("教学环节结构描述")
    @JsonProperty("teach_struct_desc")
    private String teachStructDesc;

    @ApiModelProperty("课内学时")
    @JsonProperty("in_class_hour")
    private Long inClassHour;

    @ApiModelProperty("课外学时")
    @JsonProperty("after_class_hour")
    private Long afterClassHour;

    public TeachStructVO(TeachStruct teachStruct) {
        this.teachStructId = teachStruct.getTeachStructId();
        this.teachStructDesc = teachStruct.getTeachStructDesc();
        this.inClassHour = teachStruct.getInClassHour();
        this.afterClassHour = teachStruct.getAfterClassHour();
    }

}
