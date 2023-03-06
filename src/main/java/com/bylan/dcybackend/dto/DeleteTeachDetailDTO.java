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
 * 删除教学环节细则DTO
 *
 * @author Rememorio
 */
@ApiModel("删除教学环节细则DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTeachDetailDTO implements Serializable {

    @NotEmpty(message = "教学环节细则id不能为空")
    @ApiModelProperty("须删除的教学环节细则id")
    @JsonProperty("teach_detail_ids")
    List<Long> teachDetailIds;

}