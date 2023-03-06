package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 达成度BO
 *
 * @author Rememorio
 */
@ApiModel("达成度BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReachBO {

    @ApiModelProperty("达成度")
    private Double score;
    @ApiModelProperty("是否全部打分")
    private Boolean isAllScored;

    public boolean isValid() {
        return score >= 0.0;
    }

    public static ReachBO invalidReachBO() {
        return new ReachBO(-1.0, false);
    }

}
