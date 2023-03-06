package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 毕业要求三级的简单BO
 *
 * @author Rememorio
 */
@ApiModel("毕业要求三级的简单BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleL3BO {

    @ApiModelProperty("毕业要求三级描述")
    private String l3Desc;
    @ApiModelProperty("毕业要求三级权重")
    private Double l3Weight;

    public SimpleL3BO(IloBO iloBO) {
        this.l3Desc = iloBO.getL3Desc();
        this.l3Weight = iloBO.getL3Weight();
    }
}
