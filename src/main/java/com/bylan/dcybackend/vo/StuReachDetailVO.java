package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 班级教学周达成细则VO
 *
 * @author wuhuaming
 * @date 2022/5/3 15:25
 */

@ApiModel("班级教学周达成细则VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuReachDetailVO implements Serializable {

    @ApiModelProperty("学生教学周详情——表头")
    @JsonProperty("header")
    List<StuReachDetailHeaderVO> stuReachDetailHeaderVOList;

    @ApiModelProperty("学生教学周详情——表数据")
    @JsonProperty("table_data")
    List<StuReachDetailDataVO> stuReachDetailDataVOList;

}
