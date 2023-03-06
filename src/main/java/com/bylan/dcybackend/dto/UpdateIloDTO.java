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
 * 更新ILO的DTO
 *
 * @author Rememorio
 */
@ApiModel("更新ILO的DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIloDTO implements Serializable {

    @NotNull(message = "ILO的id不能为空")
    @PositiveOrZero(message = "ILO的id不能为负数")
    @ApiModelProperty("预期学习结果id")
    @JsonProperty("ilo_id")
    private Long iloId;

    @ApiModelProperty("预期学习结果编号")
    @JsonProperty("ilo_number")
    private String iloNumber;

    @ApiModelProperty("预期学习结果描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @ApiModelProperty("初始水平")
    @JsonProperty("initial_level")
    private String initialLevel;

    @ApiModelProperty("要求水平")
    @JsonProperty("achieve_level")
    private String achieveLevel;

    @ApiModelProperty("预期学习结果权重")
    @JsonProperty("ilo_weight")
    private Double iloWeight;

    @ApiModelProperty("毕业要求三级id")
    @JsonProperty("l3_id")
    private Long l3Id;

    public Boolean isValid() {
        return iloNumber != null || iloDesc != null || initialLevel != null || achieveLevel != null || iloWeight != null || l3Id != null;
    }

}
