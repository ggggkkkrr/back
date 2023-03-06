package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 删除课程考核结构DTO
 *
 * @author Rememorio
 */
@ApiModel("删除课程考核结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAssessStructDTO implements Serializable {

    @NotEmpty(message = "课程考核结构id不能为空")
    @ApiModelProperty("须删除的课程考核结构id")
    @JsonProperty("assess_struct_ids")
    List<Long> assessStructIds;

}
