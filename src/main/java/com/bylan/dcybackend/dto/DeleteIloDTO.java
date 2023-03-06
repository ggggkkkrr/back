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
 * 删除ILO的DTO
 *
 * @author Rememorio
 */
@ApiModel("删除ILO的DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIloDTO implements Serializable {

    @NotEmpty(message = "ILO的id不能为空")
    @ApiModelProperty("须删除的ilo_id")
    @JsonProperty("ilo_ids")
    List<Long> iloIds;

}
