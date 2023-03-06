package com.bylan.dcybackend.vo;

import com.bylan.dcybackend.bo.CourseProgressBO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课程进展VO
 *
 * @author Rememorio
 * @date 2022-04-26 16:20
 */
@ApiModel("课程进展VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressVO implements Serializable {

    @ApiModelProperty("ILO描述")
    @JsonProperty("ilo_desc")
    private String iloDesc;

    @ApiModelProperty("知识点描述")
    @JsonProperty("knowledge_desc")
    private String knowledgeDesc;

    @ApiModelProperty("考核项目列表")
    @JsonProperty("assess_items")
    private AssessItem[] assessItems;

    @ApiModel("考核项目")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssessItem implements Serializable {

        @ApiModelProperty("考核项目")
        @JsonProperty("desc")
        private String desc;

        @ApiModelProperty("占比")
        @JsonProperty("proportion")
        private Double proportion;
    }

    public CourseProgressVO(CourseProgressBO courseProgressBO, List<AssessItem> assessItemList) {
        this.iloDesc = courseProgressBO.getIloDesc();
        this.knowledgeDesc = courseProgressBO.getKnowledgeDesc();
        this.assessItems = assessItemList.toArray(new AssessItem[0]);
    }

}
