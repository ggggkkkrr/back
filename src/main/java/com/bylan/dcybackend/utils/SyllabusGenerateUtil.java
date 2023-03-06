package com.bylan.dcybackend.utils;

import com.bylan.dcybackend.bo.*;
import com.bylan.dcybackend.entity.*;
import com.deepoove.poi.util.TableTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.bylan.dcybackend.domain.Constant.Path.*;
import static com.bylan.dcybackend.domain.Constant.Public.TIME_FORMAT_FOR_FILE;

/**
 * 生成教学大纲工具类
 *
 * @author Rememorio
 */
public class SyllabusGenerateUtil {

    private static final Logger log = LogManager.getLogger(SyllabusGenerateUtil.class);

    /**
     * 创建文件
     *
     * @return 空白文件
     */
    public static XWPFDocument createDocument() {
        XWPFDocument docxDocument = new XWPFDocument();
        CTSectPr sectPr = docxDocument.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(1440L));
        // 增加一、二、三级标题样式
        for (int i = 1; i <= 3; i++) {
            addCustomHeadingStyle(docxDocument, String.valueOf(i), i);
        }
        return docxDocument;
    }

    public static void addCourseSyllabus() {
        // TODO: 第一页，需要完善数据库的信息
    }

    /**
     * 课程基本信息
     *
     * @param docxDocument 文件
     * @param course       课程
     * @param curriculum   课程
     */
    public static void addCourseBasicInfo(XWPFDocument docxDocument, Course course, Curriculum curriculum) {
        // 标题1-课程基本信息
        XWPFParagraph titleDesc = docxDocument.createParagraph();
        titleDesc.setStyle("1");
        titleDesc.setSpacingBetween(2);
        titleDesc.setSpacingBefore(10);
        XWPFRun titleDescRun = titleDesc.createRun();
        setRunStyle(titleDescRun, "宋体", 12, 8, true);
        titleDescRun.setText("1、\t课程基本信息 （Course Basic Information）");

        // 正文1-课程基本信息
        int height = 6, width = 4;
        XWPFTable courseBasicInfoTable = docxDocument.createTable(height, width);

//        // 设置宽度
//        for (int i = 0; i < width; ++i) {
//            CTTcPr tcPr = courseBasicInfoTable.getRow(0).getCell(i).getCTTc().addNewTcPr();
//            CTTblWidth tableWidth = tcPr.addNewTcW();
//            tableWidth.setType(STTblWidth.DXA);
//            tableWidth.setW(BigInteger.valueOf(360 * 5));
//        }

        // 删除各个单元格原先的段落
//        for (int i = 0; i < width; i++) {
//            courseBasicInfoTable.getRow(0).getCell(i).removeParagraph(0);
//            courseBasicInfoTable.getRow(0).getCell(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
//        }
//        for (int i = 1; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                // 这里不能先移除段落，否则合并单元格时会出错
//                courseBasicInfoTable.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
//            }
//        }
        // 添加行
        int[] heights = {360 * 2, 360 * 7, 360 * 2, 360 * 2, 360 * 4, 360};
//        for (int i = 0; i < heights.length; i++) {
//            CTTrPr trPr = courseBasicInfoTable.getRow(i).getCtRow().addNewTrPr();
//            CTHeight trPrHeight = trPr.addNewTrHeight();
//            trPrHeight.setVal(BigInteger.valueOf(heights[i]));
//        }
        // 第一行：课程代码 + 课程名称
        String[] line0 = {"课程代码：", course.getCourseCode(), "课程名称：", curriculum.getCurriculumName()};
        for (int i = 0; i < line0.length; i++) {
            XWPFParagraph paragraph = courseBasicInfoTable.getRow(0).getCell(i).addParagraph();
//            XWPFParagraph paragraph = courseBasicInfoTable.getRow(0).getCell(i).getParagraphArray(0);
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            // 这里0, 2是标题，需要加粗，1, 3是正文，不用加粗
            setRunStyle(run, "宋体", 10, 6, (i & 1) == 0);
            run.setText(line0[i]);
//            courseBasicInfoTable.getRow(0).getCell(i).setText(line0[i]);
        }

        // 第二到六行
        String[][] lineOther = {
                {"课程简介：", "\t" + course.getCourseDesc()},
                {"先修要求：", "\t" + course.getPrerequisite()},
                {"课程教材：", "\t" + course.getTextbook()},
                {"推荐参考资料：", "\t" + course.getRefBook()},
                {"课程网站：", "\t" + course.getCourseWebsite()}
        };
        for (int i = 1; i < heights.length; i++) {
            for (int j = 0; j < lineOther[0].length; j++) {
                courseBasicInfoTable.getRow(i).getCell(j).removeParagraph(0);
                String[] strs = lineOther[i-1][j].split("\\r?\\n");
                for (int x = 0; x < strs.length; ++x) {
                    XWPFParagraph paragraph = courseBasicInfoTable.getRow(i).getCell(j).addParagraph();
//                XWPFParagraph paragraph = courseBasicInfoTable.getRow(i).getCell(j).getParagraphArray(0);
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun run = paragraph.createRun();
                    setRunStyle(run, "宋体", 10, 6, j == 0);
//                if (j < lineOther[0].length) {
                    run.setText(strs[x]);
//                } else {
//                    run.setText("lineOther[i - 1][j]");
//                }
                }
            }
        }

//        courseBasicInfoTable.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(1*1440));
//        for (int i = 1; i < width; ++i) {
//            courseBasicInfoTable.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(1*1440));
//        }
//        for (int col = 0; col < width; col++) {
//            CTTblWidth tblWidth = CTTblWidth.Factory.newInstance();
//            tblWidth.setW(BigInteger.valueOf(1*1440));
//            tblWidth.setType(STTblWidth.DXA);
//            for (int row = 0; row < height; row++) {
//                CTTcPr tcPr = courseBasicInfoTable.getRow(row).getCell(col).getCTTc().getTcPr();
//                if (tcPr != null) {
//                    tcPr.setTcW(tblWidth);
//                } else {
//                    tcPr = CTTcPr.Factory.newInstance();
//                    tcPr.setTcW(tblWidth);
//                    courseBasicInfoTable.getRow(row).getCell(col).getCTTc().setTcPr(tcPr);
//                }
//            }
//        }

        // 接下来的几行需要合并单元格
        for (int i = 1; i < heights.length; i++) {
            mergeCellsHorizontal(courseBasicInfoTable, i, 1, 3);
//            mergeCellHorizontally(courseBasicInfoTable, i, 1, 3);
        }
    }

    /**
     * 预期学习结果
     *
     * @param docxDocument 文件
     * @param iloBOList    预期学习结果
     */
    public static void addIntendedLearningOutcomes(XWPFDocument docxDocument, List<IloBO> iloBOList) {
        // 标题2 预期学习结果
        XWPFParagraph intendedLearningOutcomeTitle = docxDocument.createParagraph();
        intendedLearningOutcomeTitle.setSpacingBetween(2);
        intendedLearningOutcomeTitle.setSpacingBefore(10);
        intendedLearningOutcomeTitle.setStyle("1");
        XWPFRun runTitleIntendedLearningOutcomes = intendedLearningOutcomeTitle.createRun();
        setRunStyle(runTitleIntendedLearningOutcomes, "宋体", 12, 8, true);
        runTitleIntendedLearningOutcomes.setText("2、\t预期学习结果 （Intended Learning Outcomes，ILO）");
        // 正文2 预期学习结果
        // 创建表格
        int width = 10;
        int height = iloBOList.size() + 1;
        XWPFTable tableIntendedLearningOutcomes = docxDocument.createTable(height, width);
        // 表格头
        // 设置单元格高度
        CTTrPr trPrTitle = tableIntendedLearningOutcomes.getRow(0).getCtRow().addNewTrPr();
        CTHeight headHeight = trPrTitle.addNewTrHeight();
        headHeight.setVal(BigInteger.valueOf(360 * 3));
        String[] headers = {"毕业要求（二级）", "毕业要求（三级）", "权重（∑=1）", "初始程度", "要求程度", "预期学习结果（ILO）", "权重", "知识点", "权重", "预期分数"};
        int[] headerHeights = {360 * 3, 360 * 5, 360 * 3, 360 * 2, 360 * 2, 360 * 9, 360 * 2, 360 * 9, 360 * 2, 360 * 3};

        // 第一行设置宽度和文本内容
        for (int i = 0; i < width; i++) {
            tableIntendedLearningOutcomes.getRow(0).getCell(i).removeParagraph(0);
            tableIntendedLearningOutcomes.getRow(0).getCell(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            // 设置单元格宽度
            CTTcPr tcPr = tableIntendedLearningOutcomes.getRow(0).getCell(i).getCTTc().addNewTcPr();
            CTTblWidth tableWidth = tcPr.addNewTcW();
            tableWidth.setType(STTblWidth.DXA);
            tableWidth.setW(BigInteger.valueOf(headerHeights[i]));
            XWPFParagraph paragraph = tableIntendedLearningOutcomes.getRow(0).getCell(i).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 6, true);
            run.setText(headers[i]);
        }
        // 其他行设置宽度
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tableIntendedLearningOutcomes.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                // 设置单元格宽度
                CTTcPr tcPr = tableIntendedLearningOutcomes.getRow(i).getCell(j).getCTTc().addNewTcPr();
                CTTblWidth tableWidth = tcPr.addNewTcW();
                tableWidth.setType(STTblWidth.DXA);
                tableWidth.setW(BigInteger.valueOf(headerHeights[j]));
            }
        }

        // 正文 预期学习结果
        Map<String, Integer> l2Map = new HashMap<>();
        List<String> l2List = new ArrayList<>();
        Map<SimpleL3BO, Integer> l3Map = new HashMap<>();
        List<SimpleL3BO> l3List = new ArrayList<>();
        Map<SimpleIloBO, Integer> iloMap = new HashMap<>();
        List<SimpleIloBO> iloList = new ArrayList<>();
        for (IloBO iloBO : iloBOList) {
            // 对毕业要求二级去重
            String l2Desc = iloBO.getL2Desc();
            if (l2Map.containsKey(l2Desc)) {
                l2Map.put(l2Desc, l2Map.get(l2Desc) + 1);
            } else {
                l2Map.put(l2Desc, 1);
                l2List.add(l2Desc);
            }
            // 对毕业要求三级去重
            SimpleL3BO simpleL3BO = new SimpleL3BO(iloBO);
            if (l3Map.containsKey(simpleL3BO)) {
                l3Map.put(simpleL3BO, l3Map.get(simpleL3BO) + 1);
            } else {
                l3Map.put(simpleL3BO, 1);
                l3List.add(simpleL3BO);
            }
            // 对ILO去重
            SimpleIloBO simpleIloBO = new SimpleIloBO(iloBO);
            if (iloMap.containsKey(simpleIloBO)) {
                iloMap.put(simpleIloBO, iloMap.get(simpleIloBO) + 1);
            } else {
                iloMap.put(simpleIloBO, 1);
                iloList.add(simpleIloBO);
            }
        }
        // 合并第一列（毕业要求二级）的单元格
        int mergeFrom = 1;
        for (String l2Desc : l2List) {
            int number = l2Map.get(l2Desc);
            if (number > 1) {
                TableTools.mergeCellsVertically(tableIntendedLearningOutcomes, 0, mergeFrom, mergeFrom + number - 1);
            }
            tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(0).removeParagraph(0);
            XWPFParagraph paragraph = tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(0).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 8, false);
            run.setText(l2Desc);

            mergeFrom += number;
        }
        // 合并第二、三列（毕业要求三级以及权重）的单元格
        mergeFrom = 1;
        for (SimpleL3BO simpleL3Bo : l3List) {
            int number = l3Map.get(simpleL3Bo);
            for (int i = 1; i < 3; i++) {
                if (number > 1) {
                    TableTools.mergeCellsVertically(tableIntendedLearningOutcomes, i, mergeFrom, mergeFrom + number - 1);
                }
                tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(i).removeParagraph(0);
                XWPFParagraph paragraph = tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(i).addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                setRunStyle(run, "宋体", 8, 8, false);
                run.setText(switch (i) {
                    case 1 -> simpleL3Bo.getL3Desc();
                    case 2 -> String.valueOf(simpleL3Bo.getL3Weight());
                    default -> "";
                });
            }
            mergeFrom += number;
        }
        // 合并第四~七列（ILO初始要求程度、描述以及权重）的单元格
        mergeFrom = 1;
        for (SimpleIloBO simpleIloBO : iloList) {
            int number = iloMap.get(simpleIloBO);
            for (int i = 3; i < 7; i++) {
                if (number > 1) {
                    TableTools.mergeCellsVertically(tableIntendedLearningOutcomes, i, mergeFrom, mergeFrom + number - 1);
                }
                tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(i).removeParagraph(0);
                XWPFParagraph paragraph = tableIntendedLearningOutcomes.getRow(mergeFrom).getCell(i).addParagraph();
                if (i != 5) {
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                }
                XWPFRun run = paragraph.createRun();
                setRunStyle(run, "宋体", 8, 8, false);
                run.setText(switch (i) {
                    case 3 -> simpleIloBO.getInitialLevel();
                    case 4 -> simpleIloBO.getAchieveLevel();
                    case 5 -> simpleIloBO.getIloDesc();
                    case 6 -> String.valueOf(simpleIloBO.getIloWeight());
                    default -> "";
                });
            }
            mergeFrom += number;
        }
        // 写第八~十列表格
        for (int i = 0; i < iloBOList.size(); i++) {
            for (int j = 7; j < width; ++j) {
                tableIntendedLearningOutcomes.getRow(i + 1).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableIntendedLearningOutcomes.getRow(i + 1).getCell(j).removeParagraph(0);
                XWPFParagraph paragraph = tableIntendedLearningOutcomes.getRow(i + 1).getCell(j).addParagraph();
                if (j == 8 || j == 9) {
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                }
                XWPFRun run = paragraph.createRun();
                setRunStyle(run, "宋体", 8, 8, false);
                run.setText(getIntendedLearningOutcomeContent(iloBOList.get(i), j));
            }
        }
    }

    /**
     * 主要教学环节
     *
     * @param docxDocument      文件
     * @param teachStructList   教学环节结构
     * @param teachDetailBOList 教学环节细则
     */
    public static void addTeachingLearningActivities(XWPFDocument docxDocument, List<TeachStruct> teachStructList, List<TeachDetailBO> teachDetailBOList) {
        // 标题3 主要教学环节
        XWPFParagraph teachingLearningActTitle = docxDocument.createParagraph();
        teachingLearningActTitle.setSpacingBetween(2);
        XWPFRun teachingLearningActTitleRun = teachingLearningActTitle.createRun();
        setRunStyle(teachingLearningActTitleRun, "宋体", 12, 8, true);
        teachingLearningActTitleRun.setText("3、\t主要教学环节 （Teaching and Learning Activities）");
        teachingLearningActTitle.setStyle("1");

        // 标题3-1 教学环节结构
        XWPFParagraph teachStructTitle = docxDocument.createParagraph();
        teachStructTitle.setSpacingBetween(1.5);
        XWPFRun teachStructTitleRun = teachStructTitle.createRun();
        setRunStyle(teachStructTitleRun, "宋体", 10, 8, true);
        teachStructTitleRun.setText("3-1 教学环节结构");
        teachStructTitle.setStyle("2");

        // 正文3-1 教学环节结构
        // 获取教学环节结构的数据
        int width = teachStructList.size() * 2, height = 3;
        XWPFTable teachStructTable = docxDocument.createTable(3, width);
        // 合并第一行的单元格
        for (int i = 0; i < width; i += 2) {
            CTTcPr tcPr1 = teachStructTable.getRow(1).getCell(i).getCTTc().addNewTcPr();
            CTTblWidth tableWidth1 = tcPr1.addNewTcW();
            tableWidth1.setType(STTblWidth.DXA);
            tableWidth1.setW(BigInteger.valueOf(360));
            CTTcPr tcPr2 = teachStructTable.getRow(1).getCell(i + 1).getCTTc().addNewTcPr();
            CTTblWidth tableWidth2 = tcPr2.addNewTcW();
            tableWidth2.setType(STTblWidth.DXA);
            tableWidth2.setW(BigInteger.valueOf(360));

            mergeCellsHorizontal(teachStructTable, 0, i, i + 1);
            teachStructTable.getRow(0).getCell(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        }
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                teachStructTable.getRow(i).getCell(j).removeParagraph(0);
            }
        }
        for (int i = 0; i < teachStructList.size(); i++) {
            // 写第一行
            teachStructTable.getRow(0).getCell(2 * i).removeParagraph(0);
            String[][] texts = {
                    {teachStructList.get(i).getTeachStructDesc(), "（小时）"},
                    {"课内", "课外"},
                    {String.valueOf(teachStructList.get(i).getInClassHour()),
                            String.valueOf(teachStructList.get(i).getAfterClassHour())}
            };
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < texts[0].length; k++) {
                    CTTcPr tcPr = teachStructTable.getRow(j).getCell(2 * i + k).getCTTc().addNewTcPr();
                    CTTblWidth tableWidth = tcPr.addNewTcW();
                    tableWidth.setType(STTblWidth.DXA);
                    tableWidth.setW(BigInteger.valueOf(360));
                    XWPFParagraph paragraph = teachStructTable.getRow(j).getCell(2 * i + k).addParagraph();
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun run = paragraph.createRun();
                    setRunStyle(run, "宋体", 8, 8, false);
                    run.setText(texts[j][k]);
                }
            }
        }

        // 标题3-2 教学环节细节
        XWPFParagraph teachDetailTitle = docxDocument.createParagraph();
        teachDetailTitle.setSpacingBetween(1.5);
        XWPFRun teachDetailTitleRun = teachDetailTitle.createRun();
        setRunStyle(teachDetailTitleRun, "宋体", 10, 8, true);
        teachDetailTitleRun.setText("3-2 教学环节细节");
        teachDetailTitle.setStyle("2");

        // 正文3-2 教学环节细节
        // 对毕业要求二级进行去重
        HashMap<String, Integer> l2DescMap = new HashMap<>();
        List<String> l2List = new ArrayList<>();
        for (TeachDetailBO teachDetailBO : teachDetailBOList) {
            String l2Desc = teachDetailBO.getL2Desc();
            if (l2DescMap.containsKey(l2Desc)) {
                l2DescMap.put(l2Desc, l2DescMap.get(l2Desc) + 1);
            } else {
                l2DescMap.put(l2Desc, 1);
                l2List.add(l2Desc);
            }
        }
        // 表格，这里的宽度改为5
        width = 5;
        height = teachDetailBOList.size() + 1;
        XWPFTable tableSegmentRule = docxDocument.createTable(height, width);
        tableSegmentRule.getRow(0).getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        tableSegmentRule.getRow(0).getCell(0).removeParagraph(0);
        for (int i = 0; i < teachDetailBOList.size() + 1; i++) {
            for (int j = 1; j < width; j++) {
                tableSegmentRule.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableSegmentRule.getRow(i).getCell(j).removeParagraph(0);
            }
        }
        // 表头
        // 设置表头（第一行）高度
        CTTrPr trPrTitle = tableSegmentRule.getRow(0).getCtRow().addNewTrPr();
        CTHeight titleHeight = trPrTitle.addNewTrHeight();
        titleHeight.setVal(BigInteger.valueOf(360 * 2));
        String[] headers = {"毕业要求（二级）", "权重（∑=1）", "教学内容（知识单元/点）", "实现环节（课内、实验等）", "教学策略"};
        int[] headerWidths = {360 * 5, 360 * 3, 360 * 7, 360 * 5, 360 * 5};
        for (int i = 0; i < width; i++) {
            CTTcPr tcPr = tableSegmentRule.getRow(0).getCell(i).getCTTc().addNewTcPr();
            CTTblWidth tableWidth = tcPr.addNewTcW();
            tableWidth.setType(STTblWidth.DXA);
            tableWidth.setW(BigInteger.valueOf(headerWidths[i]));
            XWPFParagraph paragraph = tableSegmentRule.getRow(0).getCell(i).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 6, true);
            run.setText(headers[i]);
        }
        // 设置其他行的宽度
        for (int i = 1; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                CTTcPr tcPr = tableSegmentRule.getRow(i).getCell(j).getCTTc().addNewTcPr();
                CTTblWidth tableWidth = tcPr.addNewTcW();
                tableWidth.setType(STTblWidth.DXA);
                tableWidth.setW(BigInteger.valueOf(headerWidths[j]));
            }
        }

        // 写入第一列的数据
        // 合并第一列（毕业要求二级）的单元格
        int mergeFrom = 1;
        for (String key : l2List) {
            int number = l2DescMap.get(key);
            if (number > 1) {
                TableTools.mergeCellsVertically(tableSegmentRule, 0, mergeFrom, mergeFrom + number - 1);
            }
            tableSegmentRule.getRow(mergeFrom).getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableSegmentRule.getRow(mergeFrom).getCell(0).removeParagraph(0);
            XWPFParagraph paragraph = tableSegmentRule.getRow(mergeFrom).getCell(0).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 8, false);
            run.setText(key);
            mergeFrom += number;
        }
        // 写入第二列的数据
        for (int i = 0; i < teachDetailBOList.size(); i++) {
            XWPFParagraph paragraph = tableSegmentRule.getRow(i + 1).getCell(1).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 8, false);
            run.setText(String.valueOf(teachDetailBOList.get(i).getTeachDetailWeight()));
        }
        // 写入第三列至第五列的数据
        for (int i = 0; i < teachDetailBOList.size(); i++) {
            for (int j = 2; j < 5; ++j) {
                String[] contents = getTeachDetailBoContent(teachDetailBOList.get(i), j).split("\r\n");
                for (String content : contents) {
                    XWPFParagraph paragraph = tableSegmentRule.getRow(i + 1).getCell(j).addParagraph();
                    XWPFRun run = paragraph.createRun();
                    setRunStyle(run, "宋体", 8, 8, false);
                    run.setText(content);
                }
            }
        }
    }

    /**
     * 课程考核
     *
     * @param docxDocument             文件
     * @param assessStructList         课程考核结构
     * @param assessDetailBOList       课程考核细则
     * @param iloAssessStructBoListMap 课程考核评估标准
     */
    public static void addAssessmentScheme(XWPFDocument docxDocument,
                                           List<AssessStruct> assessStructList,
                                           List<AssessDetailBO> assessDetailBOList,
                                           Map<String, List<IloAssessStructBO>> iloAssessStructBoListMap) {
        // 标题4 课程考核
        XWPFParagraph assessmentSchemeTitle = docxDocument.createParagraph();
        assessmentSchemeTitle.setSpacingBetween(2);
        XWPFRun assessmentSchemeTitleRun = assessmentSchemeTitle.createRun();
        setRunStyle(assessmentSchemeTitleRun, "宋体", 12, 8, true);
        assessmentSchemeTitleRun.setText("4、\t课程考核 （Assessment Scheme）");
        assessmentSchemeTitle.setStyle("1");

        // 标题4-1 课程考核结构
        XWPFParagraph assessStructTitle = docxDocument.createParagraph();
        assessStructTitle.setSpacingBetween(1.5);
        XWPFRun assessStructTitleRun = assessStructTitle.createRun();
        setRunStyle(assessStructTitleRun, "宋体", 10, 8, true);
        assessStructTitleRun.setText("4-1 课程考核结构");
        assessStructTitle.setStyle("2");

        // 正文4-1 课程考核结构
        // 创建表格
        int width = 3;
        XWPFTable tableAssessStruct = docxDocument.createTable(assessStructList.size() + 1, width);
        setTableAlign(tableAssessStruct, ParagraphAlignment.CENTER);
        for (int i = 0; i < assessStructList.size() + 1; ++i) {
            for (int j = 0; j < width; ++j) {
                tableAssessStruct.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableAssessStruct.getRow(i).getCell(j).removeParagraph(0);
            }
        }
        // 设置宽度
        for (int i = 0; i < assessStructList.size() + 1; ++i) {
            for (int j = 0; j < width; j++) {
                CTTcPr tcPr = tableAssessStruct.getRow(i).getCell(j).getCTTc().addNewTcPr();
                CTTblWidth tableWidth = tcPr.addNewTcW();
                tableWidth.setType(STTblWidth.DXA);
                tableWidth.setW(BigInteger.valueOf(360 * 5));
            }
        }

        // 写入数据
        for (int i = 0; i < assessStructList.size(); ++i) {
            for (int j = 0; j < width; ++j) {
                XWPFParagraph paragraph = tableAssessStruct.getRow(i).getCell(j).addParagraph();
                XWPFRun run = paragraph.createRun();
                setRunStyle(run, "宋体", 8, 8, false);
                run.setText(getAssessStructContent(assessStructList.get(i), j));
            }
        }
        // 计算总的权重值
        double weightSum = 0;
        for (AssessStruct assessStruct : assessStructList) {
            weightSum += assessStruct.getAssessStructWeight();
        }
        String[] lastLine = {"总计", String.valueOf(weightSum), "%"};
        // 写入最后一行数据
        for (int i = 0; i < width; i++) {
            XWPFParagraph sumParagraph = tableAssessStruct.getRow(assessStructList.size()).getCell(i).addParagraph();
            XWPFRun sumParagraphRun = sumParagraph.createRun();
            setRunStyle(sumParagraphRun, "宋体", 8, 8, false);
            sumParagraphRun.setText(lastLine[i]);
        }

        // 标题4-2 课程考核细节
        XWPFParagraph assessDetailTitle = docxDocument.createParagraph();
        assessDetailTitle.setSpacingBetween(1.5);
        XWPFRun assessDetailTitleRun = assessDetailTitle.createRun();
        setRunStyle(assessDetailTitleRun, "宋体", 10, 8, true);
        assessDetailTitleRun.setText("4-2 课程考核细节");
        assessDetailTitle.setStyle("2");
        // 创建表格
        width = 4;
        int height = assessDetailBOList.size() + 1;
        XWPFTable tableAssessment = docxDocument.createTable(height, width);
        for (int i = 0; i < assessDetailBOList.size() + 1; i++) {
            for (int j = 0; j < width; j++) {
                tableAssessment.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            //  不可以在这里先移除段落，否则之后的合并单元格会报错
            //  tableAssessment.getRow(i).getCell(j).removeParagraph(0);
            }
        }
        // 表头
        // 设置表头（第一行）高度
        CTTrPr trPrHead = tableAssessment.getRow(0).getCtRow().addNewTrPr();
        CTHeight headHeight = trPrHead.addNewTrHeight();
        headHeight.setVal(BigInteger.valueOf(360 * 2));
        // 设置单元格宽度
        String[] headers = {"考核项目", "考核项目", "ILO-ID", "相关的预期学习结果（ILO）"};
        int[] headerWidths = {360 * 5, 360 * 5, 360 * 4, 360 * 9};
        // 设置宽度
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CTTcPr tcPr = tableAssessment.getRow(i).getCell(j).getCTTc().addNewTcPr();
                CTTblWidth tableWidth = tcPr.addNewTcW();
                tableWidth.setType(STTblWidth.DXA);
                tableWidth.setW(BigInteger.valueOf(headerWidths[j]));
            }
        }
        for (int i = 0; i < width; i++) {
            tableAssessment.getRow(0).getCell(i).removeParagraph(0);
            XWPFParagraph paragraph = tableAssessment.getRow(0).getCell(i).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 6, true);
            run.setText(headers[i]);
        }

        Map<String, Integer> assessTypeMap = new HashMap<>();
        List<String> assessTypeList = new ArrayList<>();
        for (AssessDetailBO assessDetailBO : assessDetailBOList) {
            String assessType = assessDetailBO.getAssessStructDesc();
            if (assessTypeMap.containsKey(assessType)) {
                assessTypeMap.put(assessType, assessTypeMap.get(assessType) + 1);
            } else {
                assessTypeMap.put(assessType, 1);
                assessTypeList.add(assessType);
            }
        }
        // 合并第一列 考核项目
        int mergeFrom = 1;
        for (String key : assessTypeList) {
            int number = assessTypeMap.get(key);
            if (number > 1) {
                TableTools.mergeCellsVertically(tableAssessment, 0, mergeFrom, mergeFrom + number - 1);
            }
            tableAssessment.getRow(mergeFrom).getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            tableAssessment.getRow(mergeFrom).getCell(0).removeParagraph(0);
            XWPFParagraph p = tableAssessment.getRow(mergeFrom).getCell(0).addParagraph();
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r = p.createRun();
            setRunStyle(r, "宋体", 8, 8, false);
            r.setText(key);

            Map<String, Integer> l2DescMap = new HashMap<>();
            List<String> l2DescList = new ArrayList<>();
            for (int i = mergeFrom; i < mergeFrom + number; ++i) {
                String l2Desc = assessDetailBOList.get(i - 1).getL2Desc();
                if (l2DescMap.containsKey(l2Desc)) {
                    l2DescMap.put(l2Desc, l2DescMap.get(l2Desc) + 1);
                } else {
                    l2DescMap.put(l2Desc, 1);
                    l2DescList.add(l2Desc);
                }
            }

            int l2MergeFrom = mergeFrom;
            for (String l2Desc : l2DescList) {
                int number2 = l2DescMap.get(l2Desc);
                if (number2 > 1) {
                    TableTools.mergeCellsVertically(tableAssessment, 1, l2MergeFrom, l2MergeFrom + number2 - 1);
                }
                tableAssessment.getRow(l2MergeFrom).getCell(1).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableAssessment.getRow(l2MergeFrom).getCell(1).removeParagraph(0);
                XWPFParagraph paragraph = tableAssessment.getRow(l2MergeFrom).getCell(1).addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                setRunStyle(run, "宋体", 8, 8, false);
                run.setText(l2Desc);

                l2MergeFrom += number2;
            }

            mergeFrom += number;
        }
        // 写第三列和第四列
        for (int i = 0; i < assessDetailBOList.size(); i++) {
            for (int j = 2; j < width; j++) {
                tableAssessment.getRow(i + 1).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableAssessment.getRow(i + 1).getCell(j).removeParagraph(0);
                XWPFParagraph p = tableAssessment.getRow(i + 1).getCell(j).addParagraph();
                if (j == 2) {
                    p.setAlignment(ParagraphAlignment.CENTER);
                }
                XWPFRun r = p.createRun();
                setRunStyle(r, "宋体", 8, 8, false);
                r.setText(getAssessDetailContent(assessDetailBOList.get(i), j));
            }
        }

        // 标题4-3 课程考核评估标准
        XWPFParagraph iloAssessStructTitle = docxDocument.createParagraph();
        iloAssessStructTitle.setSpacingBetween(1.5);
        XWPFRun iloAssessStructTitleRun = iloAssessStructTitle.createRun();
        setRunStyle(iloAssessStructTitleRun, "宋体", 10, 8, true);
        iloAssessStructTitleRun.setText("4-3 课程考核评估标准");
        iloAssessStructTitle.setStyle("2");

        // 正文4-3 根据课程考核结构，依次列出各自的ILO表格，如（平时作业，实验，期末考试等）
        int assessTypeIndex = 1;
        headers = new String[]{"预期学习结果（ILO）", "低于期望", "符合期望", "超越期望"};
        headerWidths = new int[]{360 * 3, 360 * 7, 360 * 7, 360 * 7};
        for (Map.Entry<String, List<IloAssessStructBO>> entry : iloAssessStructBoListMap.entrySet()) {
            String assessType = entry.getKey();
            List<IloAssessStructBO> iloAssessStructBOList = entry.getValue();
            // 标题
            XWPFParagraph title = docxDocument.createParagraph();
            title.setSpacingBetween(1.5);
            title.setStyle("3");
            XWPFRun titleRun = title.createRun();
            setRunStyle(titleRun, "宋体", 10, 8, true);
            titleRun.setText(String.format("考核项目%d：%s", assessTypeIndex++, assessType));
            // 创建表格
            XWPFTable table = docxDocument.createTable(iloAssessStructBOList.size() + 1, width);
            // 设置每一个单元格都垂直居中
            for (int i = 0; i < iloAssessStructBOList.size() + 1; i++) {
                for (int j = 0; j < width; j++) {
                    table.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    table.getRow(i).getCell(j).removeParagraph(0);
                }
            }
            // 表头
            // 设置表头（第一行）高度
            CTTrPr assessTypeHead = table.getRow(0).getCtRow().addNewTrPr();
            CTHeight assessHeadHeight = assessTypeHead.addNewTrHeight();
            assessHeadHeight.setVal(BigInteger.valueOf(360 * 2));
            for (int i = 0; i < width; i++) {
                // 设置单元格宽度
                CTTcPr tcPr = table.getRow(0).getCell(i).getCTTc().addNewTcPr();
                CTTblWidth tableWidth = tcPr.addNewTcW();
                tableWidth.setType(STTblWidth.DXA);
                tableWidth.setW(BigInteger.valueOf(headerWidths[i]));
                XWPFParagraph paragraph = table.getRow(0).getCell(i).addParagraph();
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                setRunStyle(run, "宋体", 8, 6, true);
                run.setText(headers[i]);
            }

            for (int i = 0; i < iloAssessStructBOList.size(); ++i) {
                for (int j = 0; j < width; ++j) {
                    CTTcPr tcPr = table.getRow(i+1).getCell(j).getCTTc().addNewTcPr();
                    CTTblWidth tableWidth = tcPr.addNewTcW();
                    tableWidth.setType(STTblWidth.DXA);
                    tableWidth.setW(BigInteger.valueOf(headerWidths[j]));

                    String[] paragraphs = getIloAssessStructContent(iloAssessStructBOList.get(i), j).split("\\r?\\n");
                    for (int x = 0; x < paragraphs.length; ++x) {
                        XWPFParagraph p = table.getRow(i + 1).getCell(j).addParagraph();
                        p.setSpacingBetween(1.25);
                        p.setSpacingBefore(1);
                        p.setAlignment(ParagraphAlignment.LEFT);
                        XWPFRun r = p.createRun();
                        setRunStyle(r, "宋体", 8, 8, false);
                        r.setText(paragraphs[x]);
                    }
                }
            }
        }
    }

    /**
     * 学习进度
     *
     * @param docxDocument       文件
     * @param courseScheduleList 学习进度
     */
    public static void addCourseSchedule(XWPFDocument docxDocument, List<CourseSchedule> courseScheduleList) {
        // 标题5 学习进度 （Course Schedule）
        XWPFParagraph courseScheduleTitle = docxDocument.createParagraph();
        courseScheduleTitle.setSpacingBetween(2);
        courseScheduleTitle.setStyle("1");
        XWPFRun courseScheduleTitleRun = courseScheduleTitle.createRun();
        setRunStyle(courseScheduleTitleRun, "宋体", 12, 8, true);
        courseScheduleTitleRun.setText("5、\t学习进度 （Course Schedule）");
        // 创建表格
        int width = 5;
        XWPFTable tableCourseSchedule = docxDocument.createTable(courseScheduleList.size() + 1, width);
        for (int i = 0; i < courseScheduleList.size() + 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                tableCourseSchedule.getRow(i).getCell(j).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                tableCourseSchedule.getRow(i).getCell(j).removeParagraph(0);
            }
        }
        // 表头
        // 设置表头（第一行）高度
        CTTrPr trPrHead = tableCourseSchedule.getRow(0).getCtRow().addNewTrPr();
        CTHeight headHeight = trPrHead.addNewTrHeight();
        headHeight.setVal(BigInteger.valueOf(360 * 3));
        // 设置单元格宽度
        String[] headers = {"周次", "教学时数", "教学形式", "教学内容", "预期学习结果"};
        int[] headerWidths = {360 * 2, 360 * 2, 360 * 4, 360 * 8, 360 * 8};
        for (int i = 0; i < courseScheduleList.size() + 1; i++) {
            for (int j = 0; j < width; j++) {
                CTTcPr trPr = tableCourseSchedule.getRow(i).getCell(j).getCTTc().addNewTcPr();
                CTTblWidth tableWidth = trPr.addNewTcW();
                tableWidth.setType(STTblWidth.DXA);
                tableWidth.setW(BigInteger.valueOf(headerWidths[j]));
            }
        }
        for (int i = 0; i < width; i++) {
            XWPFParagraph paragraph = tableCourseSchedule.getRow(0).getCell(i).addParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            setRunStyle(run, "宋体", 8, 6, true);
            run.setText(headers[i]);
        }

        // 写入表格数据 （第一列至第四列）
        for (int i = 0; i < courseScheduleList.size(); ++i) {
            for (int j = 0; j < width - 1; ++j) {
                String[] paragraphs = getCourseScheduleContent(courseScheduleList.get(i), j).split("\\r?\\n");
                for (int x = 0; x < paragraphs.length; ++x) {
                    XWPFParagraph p = tableCourseSchedule.getRow(i + 1).getCell(j).addParagraph();
                    p.setSpacingBetween(1.25);
                    p.setSpacingBefore(10);
                    if (j == 3) {
                        p.setAlignment(ParagraphAlignment.LEFT);
                    } else {
                        p.setAlignment(ParagraphAlignment.CENTER);
                    }
                    XWPFRun r = p.createRun();
                    setRunStyle(r, "宋体", 8, 8, false);
                    r.setText(paragraphs[x]);
                }
            }
        }
        // 写入表格数据 （第五列)
        List<String> courseScheduleIloList = new ArrayList<>();
        List<Integer> courseScheduleIloItemList = new ArrayList<>();
        courseScheduleIloList.add(courseScheduleList.get(0).getIlos());
        courseScheduleIloItemList.add(1);
        String pre = courseScheduleList.get(0).getIlos();
        for (int i = 1; i < courseScheduleList.size(); i++) {
            String cur = courseScheduleList.get(i).getIlos();
            if (pre.equals(cur)) {
                int size = courseScheduleIloItemList.size();
                courseScheduleIloItemList.set(size - 1, courseScheduleIloItemList.get(size - 1) + 1);
            } else {
                courseScheduleIloList.add(courseScheduleList.get(i).getIlos());
                courseScheduleIloItemList.add(1);
                pre = cur;
            }
        }
        int mergeFrom = 1;
        for (int i = 0; i < courseScheduleIloList.size(); ++i) {
            int number = courseScheduleIloItemList.get(i);
            if (number > 1) {
                TableTools.mergeCellsVertically(tableCourseSchedule, 4, mergeFrom, mergeFrom + number - 1);
            }
            tableCourseSchedule.getRow(mergeFrom).getCell(4).removeParagraph(0);
            XWPFParagraph p = tableCourseSchedule.getRow(mergeFrom).getCell(4).addParagraph();
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r = p.createRun();
            setRunStyle(r, "宋体", 8, 8, false);
            r.setText(courseScheduleIloList.get(i));
            mergeFrom += number;
        }
    }

    /**
     * 将docx文件写入文件夹
     *
     * @param docxDocument 文件
     * @param path         路径
     * @param courseCode   课程编号
     * @throws IOException IO异常
     */
    public static String createFile(XWPFDocument docxDocument, String path, String courseCode) throws IOException {
        // 获取当前日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_FOR_FILE);
        String nowStr = sdf.format(date);
        // 转化为系统路径
        path = String.join(File.separator, ROOT, path);
        // 创建一个文件夹
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("创建文件夹{}成功", path);
            } else {
                log.info("创建文件夹{}失败", path);
            }
        }
        // 创建一个文件
        String filename = SYLLABUS_DOC + "_" + nowStr + "-" + courseCode + DOC_FORMAT;
        path = String.join(File.separator, path, filename);
        file = new File(path);
        if (!file.exists()) {
            if (file.createNewFile()) {
                log.info("创建文件{}成功", path);
            } else {
                log.info("创建文件{}失败", path);
            }
        }
        // word写入到文件
        FileOutputStream fos = new FileOutputStream(path);
        docxDocument.write(fos);
        fos.close();

        return filename;
    }

    /**
     * 增加自定义标题样式。这里用的是stackoverflow的源码
     *
     * @param docxDocument 目标文档
     * @param strStyleId   样式名称
     * @param headingLevel 样式级别
     */
    private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {
        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onOff = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onOff);

        // style shows up in the formats bar
        ctStyle.setQFormat(onOff);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);
    }

    /**
     * 设置run格式
     *
     * @param run          run
     * @param fontFamily   字体
     * @param fontSize     字大小
     * @param textPosition 行间距
     * @param isBold       是否黑体
     */
    private static void setRunStyle(XWPFRun run, String fontFamily, int fontSize, int textPosition, boolean isBold) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setTextPosition(textPosition);
        run.setBold(isBold);
    }

    /**
     * word 跨列合并表格
     *
     * @param table    表格
     * @param row      行
     * @param fromCell 从哪个单元格
     * @param toCell   到哪个单元格
     */
    private static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    //merging horizontally by setting grid span instead of using CTHMerge
    static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
        XWPFTableCell cell = table.getRow(row).getCell(fromCol);
        // Try getting the TcPr. Not simply setting an new one every time.
        CTTcPr tcPr = cell.getCTTc().getTcPr();
        if (tcPr == null) tcPr = cell.getCTTc().addNewTcPr();
        // The first merged cell has grid span property set
        if (tcPr.isSetGridSpan()) {
            tcPr.getGridSpan().setVal(BigInteger.valueOf(toCol-fromCol+1));
        } else {
            tcPr.addNewGridSpan().setVal(BigInteger.valueOf(toCol-fromCol+1));
        }
        // Cells which join (merge) the first one, must be removed
        for(int colIndex = toCol; colIndex > fromCol; colIndex--) {
            table.getRow(row).getCtRow().removeTc(colIndex);
            table.getRow(row).removeCell(colIndex);
        }
    }

    /**
     * 主要教学环节细则 根据列的索引确定应该写入什么内容
     *
     * @param teachDetailBO 教学环节细则BO
     * @param index         索引
     * @return 内容
     */
    private static String getTeachDetailBoContent(TeachDetailBO teachDetailBO, int index) {
        return switch (index) {
            case 0 -> teachDetailBO.getL2Desc();
            case 1 -> String.valueOf(teachDetailBO.getTeachDetailWeight());
            case 2 -> teachDetailBO.getTeachContent();
            case 3 -> teachDetailBO.getImplLink();
            case 4 -> teachDetailBO.getTeachStrategy();
            default -> "";
        };
    }

    /**
     * 设置表格对齐格式
     *
     * @param table 表格
     * @param align 对齐格式
     */
    private static void setTableAlign(XWPFTable table, ParagraphAlignment align) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
        STJc.Enum en = STJc.Enum.forInt(align.getValue());
        jc.setVal(en);
    }

    /**
     * 课程考核结构 根据列的索引确定应该写入什么内容
     *
     * @param assessStruct 课程考核结构
     * @param index        索引
     * @return 内容
     */
    private static String getAssessStructContent(AssessStruct assessStruct, int index) {
        return switch (index) {
            case 0 -> assessStruct.getAssessStructDesc();
            case 1 -> String.valueOf(assessStruct.getAssessStructWeight() * 100);
            case 2 -> "%";
            default -> "";
        };
    }

    /**
     * 课程考核细则 根据列的索引确定应该写入什么内容
     *
     * @param assessDetailBO 课程考核细则BO
     * @param index          索引
     * @return 内容
     */
    private static String getAssessDetailContent(AssessDetailBO assessDetailBO, int index) {
        return switch (index) {
            case 0 -> assessDetailBO.getAssessStructDesc();
            case 1 -> assessDetailBO.getL2Desc();
            case 2 -> assessDetailBO.getIloNumber();
            case 3 -> assessDetailBO.getIloDesc();
            default -> "";
        };
    }

    /**
     * 课程考核评估标准 根据列的索引确定应该写入什么内容
     *
     * @param iloAssessStructBO ILO-课程考核结构
     * @param index             索引
     * @return 内容
     */
    private static String getIloAssessStructContent(IloAssessStructBO iloAssessStructBO, int index) {
        return switch (index) {
            case 0 -> iloAssessStructBO.getIloNumber();
            case 1 -> iloAssessStructBO.getUnderExpectation();
            case 2 -> iloAssessStructBO.getMeetExpectation();
            case 3 -> iloAssessStructBO.getBeyondExpectation();
            default -> "";
        };
    }

    /**
     * 学习进度 根据列的索引确定应该写入什么内容
     *
     * @param courseSchedule 学习进度
     * @param index          索引
     * @return 内容
     */
    private static String getCourseScheduleContent(CourseSchedule courseSchedule, int index) {
        return switch (index) {
            case 0 -> String.valueOf(courseSchedule.getWeek());
            case 1 -> String.valueOf(courseSchedule.getTeachingHour());
            case 2 -> courseSchedule.getTeachMode();
            case 3 -> courseSchedule.getTeachContent();
            case 4 -> courseSchedule.getIlos();
            default -> "";
        };
    }

    /**
     * 预期学习结果 根据列的索引确定应该写入什么内容
     *
     * @param iloBO 预期学习结果
     * @param index 索引
     * @return 内容
     */
    private static String getIntendedLearningOutcomeContent(IloBO iloBO, int index) {
        return switch (index) {
            case 0 -> iloBO.getL2Desc();
            case 1 -> iloBO.getL3Desc();
            case 2 -> String.valueOf(iloBO.getL3Weight());
            case 3 -> iloBO.getInitialLevel();
            case 4 -> iloBO.getAchieveLevel();
            case 5 -> iloBO.getIloDesc();
            case 6 -> String.valueOf(iloBO.getIloWeight());
            case 7 -> iloBO.getKnowledgeDesc();
            case 8 -> String.valueOf(iloBO.getKnowledgeWeight());
            case 9 -> String.valueOf(iloBO.getExpectedScore());
            default -> "";
        };
    }

}
