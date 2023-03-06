package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程进展BO
 *
 * @author Rememorio
 * @date 2022-04-29 15:32
 */
@ApiModel("课程进展BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressBO {

    @ApiModelProperty("ilo的id")
    private Long iloId;
    @ApiModelProperty("ilo描述")
    private String iloDesc;
    @ApiModelProperty("知识点id")
    private Long knowledgeId;
    @ApiModelProperty("知识点描述")
    private String knowledgeDesc;

}
