package com.bylan.dcybackend.utils;

import com.alibaba.fastjson.JSON;
import com.bylan.dcybackend.bo.*;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bylan.dcybackend.domain.Constant.Public.WINDOWS_CRLF;
import static com.bylan.dcybackend.domain.Constant.Syllabus.*;

/**
 * 解析教学大纲工具类
 *
 * @author Rememorio
 */
public class SyllabusParseUtil {

    private static final Logger log = LogManager.getLogger(SyllabusParseUtil.class);

    private static String processCell(XWPFTableCell cell) {
        StringBuilder content = new StringBuilder();
        List<XWPFParagraph> cellPages = cell.getParagraphs();
        for (int i = 0; i < cellPages.size(); i++) {
            content.append(cellPages.get(i).getText());
            if (i != cellPages.size() - 1) {
                content.append(WINDOWS_CRLF);
            }
        }
        return content.toString();
    }

    /**
     * 在字符串的字母-数字之间或者数字-字母之间添加指定字符
     * 用于处理ILO1编程ILO-1之类的问题
     *
     * @param str 字符串
     * @param ch  指定字符
     * @return 处理后的字符
     */
    public static String addChar(String str, char ch) {
        StringBuilder sb = new StringBuilder();
        String[] temp = str.split(ALPHABET_NUMBER_REG);
        if (temp.length <= 1) {
            return str;
        }
        sb.append(temp[0]);
        for (int i = 1; i < temp.length; i++) {
            sb.append(ch);
            sb.append(temp[i]);
        }
        return sb.toString();
    }

    /**
     * 解析课程
     *
     * @param table    表格
     * @param courseId 课程id
     * @return 课程
     */
    public static Course parseCourse(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<XWPFTableRow> rows = table.getRows();
        Map<String, String> map = new HashMap<>(COURSE_BASE_INFORMATION.length);
        for (int i = 0; i < COURSE_BASE_INFORMATION.length; i++) {
            map.put(COURSE_BASE_INFORMATION[i], processCell(rows.get(i).getCell(1)));
        }
        Course course = JSON.parseObject(JSON.toJSONString(map), Course.class);
        course.setCourseId(courseId);
        return course;
    }

    /**
     * 解析ILO
     *
     * @param table    表格
     * @param courseId 课程id
     * @return ILO的BO
     */
    public static List<IloBO> parseIlo(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size(); i++) {
            Map<String, Object> map = new HashMap<>(INTENDED_LEARNING_OUTCOMES.length);
            XWPFTableRow row = rows.get(i);
            // 读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            // 如果单元格长度不对则跳过，表格最后会有一行备注
            if (cells.size() < INTENDED_LEARNING_OUTCOMES.length) {
                continue;
            }
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                //输出当前的单元格的数据
                String content = processCell(cell);
                if (content.length() == 0) {
                    Object lastData = result.get(Math.max(0, i - 2)).get(INTENDED_LEARNING_OUTCOMES[j]);
                    map.put(INTENDED_LEARNING_OUTCOMES[j], lastData);
                    continue;
                }
                // 权重是double类型，需要解析
                if (j == 2 || j == 6 || j == 8 || j == 9) {
                    Double weight = Double.parseDouble(content);
                    map.put(INTENDED_LEARNING_OUTCOMES[j], weight);
                    continue;
                }
                map.put(INTENDED_LEARNING_OUTCOMES[j], content);
            }
            result.add(map);
        }
        List<IloBO> iloBOList = new ArrayList<>();
        for (Map<String, Object> map : result) {
            IloBO iloBO = JSON.parseObject(JSON.toJSONString(map), IloBO.class);
            iloBO.setCourseId(courseId);
            iloBOList.add(iloBO);
        }
        return iloBOList;
    }

    /**
     * 解析教学环节结构
     *
     * @param table    表格
     * @param courseId 课程id
     * @return 教学环节结构
     */
    public static List<TeachStruct> parseTeachStruct(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<TeachStruct> result = new ArrayList<>();

        List<XWPFTableRow> rows = table.getRows();
        // 表头
        List<XWPFTableCell> headCells = rows.get(0).getTableCells();
        for (XWPFTableCell cell : headCells) {
            String courseDesc = processCell(cell).replace("(小时)", "");
            courseDesc = courseDesc.replace(WINDOWS_CRLF, "");
            TeachStruct teachStruct = new TeachStruct(courseId, courseDesc);
            result.add(teachStruct);
        }
        // 学时
        XWPFTableRow row = rows.get(2);
        // 读取每一列数据
        List<XWPFTableCell> hourCells = row.getTableCells();
        for (int j = 0; j < hourCells.size() / 2; j++) {
            // 输出当前的单元格的数据
            Long inClassHour = Long.parseLong(hourCells.get(2 * j).getText());
            Long afterClassHour = Long.parseLong(hourCells.get(2 * j + 1).getText());
            result.get(j).setInClassHour(inClassHour);
            result.get(j).setAfterClassHour(afterClassHour);
        }
        return result;
    }

    /**
     * 解析教学环节细则
     *
     * @param table    表格
     * @param courseId 课程id
     * @return TeachDetail的BO
     */
    public static List<TeachDetailBO> parseTeachDetail(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size(); i++) {
            Map<String, Object> map = new HashMap<>(TEACHING_DETAIL.length);
            XWPFTableRow row = rows.get(i);
            // 读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                // 输出当前的单元格的数据
                String content = processCell(cell);
                if (content.length() == 0) {
                    Object lastData = result.get(Math.max(0, i - 2)).get(TEACHING_DETAIL[j]);
                    map.put(TEACHING_DETAIL[j], lastData);
                    continue;
                }
                // 权重是double类型，需要解析
                if (j == 1) {
                    Double weight = Double.parseDouble(content);
                    map.put(TEACHING_DETAIL[j], weight);
                    continue;
                }
                map.put(TEACHING_DETAIL[j], content);
            }
            result.add(map);
        }
        List<TeachDetailBO> teachDetailBOList = new ArrayList<>();
        for (Map<String, Object> map : result) {
            TeachDetailBO teachDetailBO = JSON.parseObject(JSON.toJSONString(map), TeachDetailBO.class);
            teachDetailBO.setCourseId(courseId);
            teachDetailBOList.add(teachDetailBO);
        }
        return teachDetailBOList;
    }

    /**
     * 解析课程考核结构
     *
     * @param table    表格
     * @param courseId 课程id
     * @return 课程考核结构
     */
    public static List<AssessStruct> parseAssessStruct(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<AssessStruct> assessStructList = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        // 最后一行是总计，因此-1
        for (int i = 0; i < rows.size() - 1; i++) {
            XWPFTableRow row = rows.get(i);
            //读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            String assessStructDesc = cells.get(0).getText();
            Double assessStructWeight = Long.parseLong(cells.get(1).getText()) * 0.01;
            AssessStruct assessStruct = new AssessStruct(courseId, assessStructWeight, assessStructDesc);
            assessStructList.add(assessStruct);
        }
        return assessStructList;
    }

    /**
     * 在解析教学大纲时动态获取考核结构
     *
     * @param table 表格
     * @return 课程考核结构描述
     */
    public static String[] parseAssessStruct(XWPFTable table) {
        if (table == null) {
            return null;
        }
        List<String> assessStructDescList = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size() - 1; i++) {
            XWPFTableRow row = rows.get(i);
            //读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            String assessStructDesc = cells.get(0).getText();
            assessStructDescList.add(assessStructDesc);
        }
        return assessStructDescList.toArray(new String[0]);
    }

    /**
     * 解析课程考核细则
     *
     * @param table    表格
     * @param courseId 课程id
     * @return 课程考核细则
     */
    public static List<AssessDetailBO> parseAssessDetail(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<Map<String, String>> result = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size(); i++) {
            Map<String, String> map = new HashMap<>(ASSESS_DETAIL.length);
            XWPFTableRow row = rows.get(i);
            // 读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                // 输出当前的单元格的数据
                String content = processCell(cell);
                if (content.length() == 0) {
                    String lastData = result.get(Math.max(0, i - 2)).get(ASSESS_DETAIL[j]);
                    map.put(ASSESS_DETAIL[j], lastData);
                    continue;
                }
                map.put(ASSESS_DETAIL[j], content);
            }
            result.add(map);
        }
        List<AssessDetailBO> assessDetailBOList = new ArrayList<>();
        for (Map<String, String> map : result) {
            AssessDetailBO assessDetailBO = JSON.parseObject(JSON.toJSONString(map), AssessDetailBO.class);
            assessDetailBO.setCourseId(courseId);
            assessDetailBOList.add(assessDetailBO);
        }
        return assessDetailBOList;
    }

    /**
     * 解析课程考核评估标准
     *
     * @param tables   表格s
     * @param courseId 课程id
     * @return ILO-课程考核结构
     */
    public static List<IloAssessStructBO> parseIloAssessStruct(Map<String, XWPFTable> tables, Long courseId) {
        if (tables == null) {
            return null;
        }
        List<Map<String, String>> result = new ArrayList<>();
        for (Map.Entry<String, XWPFTable> table : tables.entrySet()) {
            List<XWPFTableRow> rows = table.getValue().getRows();
            for (int i = 1; i < rows.size(); i++) {
                Map<String, String> map = new HashMap<>(ILO_ASSESS_STRUCT.length);
                XWPFTableRow row = rows.get(i);
                // 读取每一列数据
                List<XWPFTableCell> cells = row.getTableCells();
                for (int j = 0; j < cells.size(); j++) {
                    XWPFTableCell cell = cells.get(j);
                    // 输出当前的单元格的数据
                    String content = processCell(cell);
                    if (content.length() == 0) {
                        String lastData = result.get(Math.max(0, i - 2)).get(ILO_ASSESS_STRUCT[j]);
                        map.put(ILO_ASSESS_STRUCT[j], lastData);
                        continue;
                    }
                    map.put(ILO_ASSESS_STRUCT[j], content);
                }
                // 把课程考核结构的描述放进去
                map.put(ILO_ASSESS_STRUCT[4], table.getKey());
                result.add(map);
            }
        }
        List<IloAssessStructBO> iloAssessStructBOList = new ArrayList<>();
        for (Map<String, String> map : result) {
            IloAssessStructBO iloAssessStructBO = JSON.parseObject(JSON.toJSONString(map), IloAssessStructBO.class);
            iloAssessStructBO.setCourseId(courseId);
            iloAssessStructBOList.add(iloAssessStructBO);
        }
        return iloAssessStructBOList;
    }

    /**
     * 解析学习进度
     *
     * @param table    表格
     * @param courseId 课程id
     * @return 学习进度
     */
    public static List<CourseSchedule> parseCourseSchedule(XWPFTable table, Long courseId) {
        if (table == null) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size(); i++) {
            Map<String, Object> map = new HashMap<>(COURSE_SCHEDULE.length);
            XWPFTableRow row = rows.get(i);
            // 读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                // 输出当前的单元格的数据
                String content = processCell(cell);
                if (content.length() == 0) {
                    Object lastData = result.get(Math.max(0, i - 2)).get(COURSE_SCHEDULE[j]);
                    map.put(COURSE_SCHEDULE[j], lastData);
                    continue;
                }
                // 周次和教学时数是long类型，需要解析
                if (j == 0 || j == 1) {
                    Long number = Long.parseLong(content);
                    map.put(COURSE_SCHEDULE[j], number);
                    continue;
                }
                // ilo需要以分号分割
                if (j == 4) {
                    content = content.replace(WINDOWS_CRLF, Constant.Public.SEPARATOR);
                }
                map.put(COURSE_SCHEDULE[j], content);
            }
            result.add(map);
        }
        List<CourseSchedule> courseScheduleList = new ArrayList<>();
        for (Map<String, Object> map : result) {
            CourseSchedule courseSchedule = JSON.parseObject(JSON.toJSONString(map), CourseSchedule.class);
            courseSchedule.setCourseId(courseId);
            courseScheduleList.add(courseSchedule);
        }
        return courseScheduleList;
    }

    /**
     * 解析实验
     *
     * @param tables   表格s
     * @param courseId 课程id
     * @return 实验
     */
    public static List<Experiment> parseExperiment(List<XWPFTable> tables, Long courseId) {
        if (tables == null) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            Map<String, Object> map = new HashMap<>(EXPERIMENT.length);
            // 实验名称
            map.put(EXPERIMENT[0], rows.get(0).getTableCells().get(1).getText());
            // 序号
            map.put(EXPERIMENT[1], rows.get(1).getTableCells().get(1).getText());
            // 学时，需要解析long
            map.put(EXPERIMENT[2], Long.parseLong(rows.get(1).getTableCells().get(3).getText()));
            for (int j = 2; j < rows.size(); j++) {
                XWPFTableRow row = rows.get(j);
                String content = processCell(row.getCell(1));
                map.put(EXPERIMENT[j + 1], content);
            }
            result.add(map);
        }
        List<Experiment> experimentList = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Experiment experiment = JSON.parseObject(JSON.toJSONString(map), Experiment.class);
            experiment.setCourseId(courseId);
            experimentList.add(experiment);
        }
        return experimentList;
    }

    /**
     * 解析实践
     *
     * @param tables   表格s
     * @param courseId 课程id
     * @return 实验
     */
    public static List<Practice> parsePractice(List<XWPFTable> tables, Long courseId) {
        if (tables == null) {
            return null;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            Map<String, Object> map = new HashMap<>(PRACTICE.length);
            // 实践名称
            map.put(PRACTICE[0], rows.get(0).getTableCells().get(1).getText());
            // 序号
            map.put(PRACTICE[1], rows.get(1).getTableCells().get(1).getText());
            // 学时，需要解析long
            map.put(PRACTICE[2], Long.parseLong(rows.get(1).getTableCells().get(3).getText()));
            for (int j = 2; j < rows.size(); j++) {
                XWPFTableRow row = rows.get(j);
                String content = processCell(row.getCell(1));
                map.put(PRACTICE[j + 1], content);
            }
            result.add(map);
        }
        List<Practice> practiceList = new ArrayList<>();
        for (Map<String, Object> map : result) {
            Practice practice = JSON.parseObject(JSON.toJSONString(map), Practice.class);
            practice.setCourseId(courseId);
            practiceList.add(practice);
        }
        return practiceList;
    }

    /**
     * 获取实验的备注
     *
     * @param table       实验汇总表格
     * @param experiments 解析好就差备注内容的实验列表
     */
    public static void parseExperimentSummary(XWPFTable table, List<Experiment> experiments) {
        if (table == null) {
            return;
        }
        Map<String, String> map = new HashMap<>(2);
        List<XWPFTableRow> rows = table.getRows();
        // 最后一行是合计
        for (int i = 1; i < rows.size() - 1; i++) {
            XWPFTableRow row = rows.get(i);
            // 读取实验序号和备注
            String experimentNumber = row.getCell(0).getText();
            String remark = row.getCell(3).getText();
            map.put(experimentNumber, remark);
        }
        for (Experiment experiment : experiments) {
            String remark = map.getOrDefault(experiment.getExperimentNumber(), "");
            if (!"".equals(remark)) {
                experiment.setRemark(remark);
            }
        }
    }

    /**
     * 获取实验的备注
     *
     * @param table     实验汇总表格
     * @param practices 解析好就差备注内容的实验列表
     */
    public static void parsePracticeSummary(XWPFTable table, List<Practice> practices) {
        if (table == null) {
            return;
        }
        Map<String, String> map = new HashMap<>(2);
        List<XWPFTableRow> rows = table.getRows();
        // 最后一行是合计
        for (int i = 1; i < rows.size() - 1; i++) {
            XWPFTableRow row = rows.get(i);
            // 读取实验序号和备注
            String practiceNumber = row.getCell(0).getText();
            String remark = row.getCell(3).getText();
            map.put(practiceNumber, remark);
        }
        for (Practice practice : practices) {
            String remark = map.getOrDefault(practice.getPracticeNumber(), "");
            if (!"".equals(remark)) {
                practice.setRemark(remark);
            }
        }
    }

    /**
     * 解析教学大纲
     *
     * @param document 教学大纲文档
     * @return 教学大纲表格BO
     */
    public static SyllabusTableBO parseSyllabus(XWPFDocument document) {
        if (document == null) {
            return null;
        }
        List<XWPFTable> tables = document.getTables();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        if (tables == null || paragraphs == null) {
            return null;
        }
        SyllabusTableBO syllabusTableBO = new SyllabusTableBO();
        int i = 1;
        String reg;
        String[] evaluations = null;
        for (XWPFParagraph paragraph : paragraphs) {
            String words = paragraph.getText().trim().replace(WINDOWS_CRLF, "");
            if (words.length() <= 0) {
                continue;
            }
            if (paragraph.getStyleID() != null) {
                reg = "[1-6]";
                if (match(reg, paragraph.getStyleID())) {
                    if (words.contains("课程基本信息")) {
                        syllabusTableBO.setCourseTable(tables.get(i));
                        i++;
                    }
                    if (words.contains("预期学习结果")) {
                        syllabusTableBO.setIloTable(tables.get(i));
                        i++;
                    }
                    if (words.contains("教学环节结构")) {
                        syllabusTableBO.setTeachStructTable(tables.get(i));
                        i++;
                    }
                    if (words.contains("教学环节细则")) {
                        syllabusTableBO.setTeachDetailTable(tables.get(i));
                        i++;
                    }
                    if (words.contains("课程考核结构")) {
                        syllabusTableBO.setAssessStructTable(tables.get(i));
                        // 写入课程考核结构
                        evaluations = SyllabusParseUtil.parseAssessStruct(tables.get(i));
                        i++;
                    }
                    if (words.contains("课程考核细则")) {
                        syllabusTableBO.setAssessDetailTable(tables.get(i));
                        i++;
                    }
                    reg = ".*(考核项目[0-9]+).*";
                    if (match(reg, words)) {
                        if (evaluations != null) {
                            for (String evaluation : evaluations) {
                                if (words.contains(evaluation)) {
                                    syllabusTableBO.getIloAssessStructTables().put(evaluation, tables.get(i));
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                    if (words.contains("学习进度")) {
                        syllabusTableBO.setCourseScheduleTable(tables.get(i));
                        i++;
                    }
                    reg = ".*(实验环节汇总表).*";
                    if (match(reg, words)) {
                        syllabusTableBO.setExperimentSummaryTable(tables.get(i));
                        i++;
                    }
                    reg = ".*(实践环节汇总表).*";
                    if (match(reg, words)) {
                        syllabusTableBO.setPracticeSummaryTable(tables.get(i));
                        i++;
                    }
                    reg = ".*(实验[一二三四五六七八九十]+).*";
                    if (match(reg, words)) {
                        syllabusTableBO.getExperimentTables().add(tables.get(i));
                        i++;
                    }
                    reg = ".*(实践[一二三四五六七八九十]+).*";
                    if (match(reg, words)) {
                        syllabusTableBO.getPracticeTables().add(tables.get(i));
                        i++;
                    }
                }
            }
        }
        return syllabusTableBO;
    }

    private static boolean match(String reg, String words) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(words);
        return matcher.matches();
    }

}
