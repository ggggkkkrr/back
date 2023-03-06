package com.bylan.dcybackend.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教学大纲表格
 *
 * @author Rememorio
 */
@ApiModel("教学大纲表格")
@Data
@AllArgsConstructor
public class SyllabusTableBO {

    @ApiModelProperty("课程基本信息")
    private XWPFTable courseTable;
    @ApiModelProperty("ILO")
    private XWPFTable iloTable;
    @ApiModelProperty("教学环节结构")
    private XWPFTable teachStructTable;
    @ApiModelProperty("教学环节细则")
    private XWPFTable teachDetailTable;
    @ApiModelProperty("课程考核结构")
    private XWPFTable assessStructTable;
    @ApiModelProperty("课程考核细则")
    private XWPFTable assessDetailTable;
    @ApiModelProperty("考核项目")
    private Map<String, XWPFTable> iloAssessStructTables;
    @ApiModelProperty("学习进度")
    private XWPFTable courseScheduleTable;
    @ApiModelProperty("实验汇总")
    XWPFTable experimentSummaryTable;
    @ApiModelProperty("实践汇总")
    XWPFTable practiceSummaryTable;
    @ApiModelProperty("实验")
    private List<XWPFTable> experimentTables;
    @ApiModelProperty("实践")
    private List<XWPFTable> practiceTables;

    public SyllabusTableBO() {
        courseTable = null;
        iloTable = null;
        teachStructTable = null;
        teachDetailTable = null;
        assessStructTable = null;
        assessDetailTable = null;
        iloAssessStructTables = new HashMap<>();
        courseScheduleTable = null;
        experimentSummaryTable = null;
        practiceSummaryTable = null;
        experimentTables = new ArrayList<>();
        practiceTables = new ArrayList<>();
    }

}
