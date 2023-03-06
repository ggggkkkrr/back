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
 * 删除教学环节结构DTO
 *
 * @author Rememorio
 */
@ApiModel("删除教学环节结构DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTeachStructDTO implements Serializable {

    @NotEmpty(message = "教学环节结构id不能为空")
    @ApiModelProperty("须删除的教学环节结构id")
    @JsonProperty("teach_struct_ids")
    List<Long> teachStructIds;

}
