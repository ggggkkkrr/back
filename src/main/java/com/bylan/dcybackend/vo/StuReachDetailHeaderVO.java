package com.bylan.dcybackend.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 班级教学周达成细则-表头VO
 *
 * @author wuhuaming
 * @date 2022/5/3 15:31
 */
@ApiModel("班级教学周达成细则-表头VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuReachDetailHeaderVO implements Serializable {

    @ApiModelProperty("题目id")
    @JsonProperty("task_id")
    private Long taskId;

    @ApiModelProperty("题目描述")
    @JsonProperty("task_desc")
    private String taskDesc;

    @ApiModelProperty("题目名字")
    @JsonProperty("task_name")
    private String taskName;

}
