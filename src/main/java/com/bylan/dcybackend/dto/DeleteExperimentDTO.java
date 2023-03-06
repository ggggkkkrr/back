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
 * 删除实验DTO
 *
 * @author Rememorio
 */
@ApiModel("删除实验DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteExperimentDTO implements Serializable {

    @NotEmpty(message = "实验id不能为空")
    @ApiModelProperty("须删除的实验id")
    @JsonProperty("experiment_ids")
    List<Long> experimentIds;

}