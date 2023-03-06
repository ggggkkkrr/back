package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程考核细则BO
 *
 * @author Rememorio
 */
@ApiModel("课程考核细则BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessDetailBO {

    @ApiModelProperty("课程考核结构描述")
    private String assessStructDesc;
    @ApiModelProperty("毕业要求二级描述")
    private String l2Desc;
    @ApiModelProperty("预期学习结果编号")
    private String iloNumber;
    @ApiModelProperty("预期学习结果描述")
    private String iloDesc;
    @ApiModelProperty("课程id")
    private Long courseId;

}
