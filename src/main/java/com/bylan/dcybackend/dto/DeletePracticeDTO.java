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
 * 删除实践DTO
 *
 * @author Rememorio
 */
@ApiModel("删除实践DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePracticeDTO implements Serializable {

    @NotEmpty(message = "实践id不能为空")
    @ApiModelProperty("须删除的实践id")
    @JsonProperty("practice_ids")
    List<Long> practiceIds;

}