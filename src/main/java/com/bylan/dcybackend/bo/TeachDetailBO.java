package com.bylan.dcybackend.bo;

import com.bylan.dcybackend.entity.TeachDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教学环节细则BO
 *
 * @author Rememorio
 */
@ApiModel("教学环节细则BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachDetailBO {

    @ApiModelProperty("毕业要求二级描述")
    private String l2Desc;
    @ApiModelProperty("教学环节细则权重")
    private Double teachDetailWeight;
    @ApiModelProperty("教学内容")
    private String teachContent;
    @ApiModelProperty("实现环节")
    private String implLink;
    @ApiModelProperty("教学策略")
    private String teachStrategy;
    @ApiModelProperty("课程id")
    private Long courseId;

    public TeachDetailBO(TeachDetail teachDetail) {
        this.teachDetailWeight = teachDetail.getTeachDetailWeight();
        this.teachContent = teachDetail.getTeachContent();
        this.implLink = teachDetail.getImplLink();
        this.teachStrategy = teachDetail.getTeachStrategy();
        this.courseId = teachDetail.getCourseId();
    }

}
