package com.bylan.dcybackend.bo;

import com.bylan.dcybackend.entity.KnowledgePoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bylan.dcybackend.domain.Constant.Syllabus.*;
import static com.bylan.dcybackend.utils.SyllabusParseUtil.addChar;

/**
 * Ilo的BO
 *
 * @author Rememorio
 */
@ApiModel("Ilo的BO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IloBO {

    @ApiModelProperty("毕业要求二级描述")
    private String l2Desc;
    @ApiModelProperty("毕业要求三级描述")
    private String l3Desc;
    @ApiModelProperty("毕业要求三级权重")
    private Double l3Weight;
    @ApiModelProperty("初始水平")
    private String initialLevel;
    @ApiModelProperty("要求水平")
    private String achieveLevel;
    @ApiModelProperty("预期学习结果编号")
    private String iloNumber;
    @ApiModelProperty("预期学习结果描述")
    private String iloDesc;
    @ApiModelProperty("ilo权重")
    private Double iloWeight;
    @ApiModelProperty("知识点编号")
    private String knowledgeNumber;
    @ApiModelProperty("知识点描述")
    private String knowledgeDesc;
    @ApiModelProperty("知识点权重")
    private Double knowledgeWeight;
    @ApiModelProperty("预期分数")
    private Double expectedScore;
    @ApiModelProperty("课程id")
    private Long courseId;

    // 以下字段需要被填充

    @ApiModelProperty("毕业要求三级id")
    private Long l3Id;
    @ApiModelProperty("预期学习结果id")
    private Long iloId;
    @ApiModelProperty("知识点id")
    private Long knowledgeId;


    public void setIloNumberByDesc() {
        if (iloDesc == null) {
            iloNumber = ILO_X;
        }
        Pattern pattern = Pattern.compile(ILO_REG);
        Matcher matcher = pattern.matcher(iloDesc);
        if (!matcher.find()) {
            return;
        }
        iloNumber = addChar(matcher.group(), CONNECTOR);
        iloDesc = iloDesc.replace(matcher.group(), iloNumber);
    }

    public void setKnowledgeNumberByDesc() {
        if (knowledgeDesc == null) {
            knowledgeNumber = ILO_X_X;
        }
        Pattern pattern = Pattern.compile(KNOWLEDGE_REG);
        Matcher matcher = pattern.matcher(knowledgeDesc);
        if (!matcher.find()) {
            return;
        }
        knowledgeNumber = addChar(matcher.group(), CONNECTOR);
        knowledgeDesc = knowledgeDesc.replace(matcher.group(), knowledgeNumber);
    }

    public IloBO(KnowledgePoint knowledgePoint) {
        this.knowledgeId = knowledgePoint.getKnowledgeId();
        this.knowledgeNumber = knowledgePoint.getKnowledgeNumber();
        this.knowledgeDesc = knowledgePoint.getKnowledgeDesc();
        this.knowledgeWeight = knowledgePoint.getKnowledgeWeight();
        this.expectedScore = knowledgePoint.getExpectedScore();
    }

}
