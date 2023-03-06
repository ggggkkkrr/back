package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.bo.*;
import com.bylan.dcybackend.dao.*;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.UpdateCourseDTO;
import com.bylan.dcybackend.entity.*;
import com.bylan.dcybackend.service.SyllabusService;
import com.bylan.dcybackend.utils.FileProcessUtil;
import com.bylan.dcybackend.utils.SyllabusGenerateUtil;
import com.bylan.dcybackend.utils.SyllabusParseUtil;
import com.bylan.dcybackend.vo.CourseVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.bylan.dcybackend.domain.Constant.Path.*;
import static com.bylan.dcybackend.domain.Constant.Public.TIME_FORMAT_FOR_FILE;
import static com.bylan.dcybackend.utils.FileProcessUtil.convertFilename;
import static com.bylan.dcybackend.utils.SyllabusParseUtil.*;

/**
 * 教学大纲相关服务
 *
 * @author Rememorio
 */
@Service
public class SyllabusServiceImpl implements SyllabusService {

    private static final Logger log = LogManager.getLogger(SyllabusServiceImpl.class);

    @Autowired
    CourseDAO courseDAO;

    @Autowired
    CurriculumDAO curriculumDAO;

    @Autowired
    GraduateRequirementL2DAO graduateRequirementL2DAO;

    @Autowired
    GraduateRequirementL3DAO graduateRequirementL3DAO;

    @Autowired
    IloDAO iloDAO;

    @Autowired
    AssessStructDAO assessStructDAO;

    @Autowired
    IloAssessStructDAO iloAssessStructDAO;

    @Autowired
    KnowledgePointDAO knowledgePointDAO;

    @Autowired
    QuestionKnowledgePointDAO questionKnowledgePointDAO;

    @Autowired
    TeachStructDAO teachStructDAO;

    @Autowired
    TeachDetailDAO teachDetailDAO;

    @Autowired
    CourseScheduleDAO courseScheduleDAO;

    @Autowired
    ExperimentDAO experimentDAO;

    @Autowired
    PracticeDAO practiceDAO;

    @Resource
    private DocumentConverter documentConverter;

    /**
     * 更新单个课程
     *
     * @param updateCourseDTO 更新课程DTO
     * @return 成功
     */
    @Override
    public Boolean updateCourse(UpdateCourseDTO updateCourseDTO) {
        Course course = new Course(updateCourseDTO);
        try {
            courseDAO.update(course);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("插入course时失败 堆栈信息: {}; 数据信息: {}", e, course);
            return false;
        }
        return true;
    }

    /**
     * 获取单个课程
     *
     * @param courseId 课程id
     * @return 课程
     */
    @Override
    public CourseVO getCourseByCourseId(Long courseId) {
        Course course = courseDAO.queryById(courseId);
        return new CourseVO(course);
    }

    /**
     * 下载大纲模板
     *
     * @param response 响应
     */
    @Override
    public void downloadTemplate(HttpServletResponse response) {
        String path = String.join(File.separator, SYLLABUS, Constant.Path.TEMPLATE);
        FileProcessUtil.downloadFile(path, response);
    }

    /**
     * 上传教学大纲
     *
     * @param file 文件
     */
    @Override
    public String uploadSyllabus(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || "".equals(originalFilename)) {
            throw new FileNotFoundException("文件名为空");
        }
        return FileProcessUtil.uploadFile(file, SYLLABUS, originalFilename);
    }

    @Override
    public Boolean deleteSyllabusFile(String filename) {
        String path = String.join(File.separator, SYLLABUS, filename);
        return FileProcessUtil.deleteFile(path);
    }

    /**
     * 下载教学大纲
     *
     * @param filename 文件名
     */
    @Override
    public void downloadSyllabus(String filename, HttpServletResponse response) {
        String path = String.join(File.separator, SYLLABUS, filename);
        FileProcessUtil.downloadFile(path, response);
    }

    /**
     * 解析教学大纲
     *
     * @param file     文件
     * @param courseId 课程id
     * @throws IOException 数据库异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseSyllabus(MultipartFile file, Long courseId) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || "".equals(originalFilename)) {
            throw new FileNotFoundException("文件名为空");
        }
        if (!originalFilename.endsWith(Constant.Path.DOC_FORMAT)) {
            throw new InvalidObjectException("文件格式不支持");
        }
        String tempFilename = convertFilename(TEMP_FILE);
        String path = String.join(File.separator, ROOT, SYLLABUS, tempFilename);
        File tempFile = new File(path);
        // 创建临时文件
        if (!tempFile.exists()) {
            if (!tempFile.createNewFile()) {
                throw new IOException("文件夹创建失败");
            }
        }
        // 复制文件内容
        FileCopyUtils.copy(file.getBytes(), tempFile);
        // 包含所有POI OO XML文档类的通用功能，打开一个文件包
        OPCPackage opcPackage = POIXMLDocument.openPackage(path);
        // 使用XWPF组件XWPFDocument类获取文档内容
        XWPFDocument document = new XWPFDocument(opcPackage);
        // 进行表格归属的判断
        SyllabusTableBO syllabusTableBO = SyllabusParseUtil.parseSyllabus(document);
        // 解析并插入
        try {
            accessDatabase(syllabusTableBO, courseId);
        } catch (Exception e) {
            log.error("文件内容不合法");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new InvalidObjectException(e.getMessage());
        }
        // 删除临时文件
        finally {
            try {
                opcPackage.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("关闭文件失败");
            }
            log.info("删除临时文件{}状态：{}", tempFile.getName(), tempFile.delete());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accessDatabase(SyllabusTableBO syllabusTableBO, Long courseId) throws InvalidObjectException {
        // 课程基本信息
        Course course = parseCourse(syllabusTableBO.getCourseTable(), courseId);
        if (course == null) {
            throw new InvalidObjectException("文件内容不合法，缺失课程基本信息");
        }
        courseDAO.update(course);
        // 预期学习结果
        List<IloBO> iloBOList = parseIlo(syllabusTableBO.getIloTable(), courseId);
        if (iloBOList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失预期学习结果");
        }
        // 填充iloBO的部分字段
        processIloBo(iloBOList);
        // 对iloBO的ilo部分进行去重
        List<Ilo> iloList = iloBo2Ilo(iloBOList);
        // 保存旧的iloId，为下一步删除ilo_assess_struct表作铺垫
        List<Long> iloIds = iloDAO.queryAllByCourseId(courseId);
        iloDAO.mDeleteByCourseId(courseId);
        iloDAO.mInsert(iloList);
        // 预期学习结果中的知识点，填充回填的ilo字段
        processIloBo(iloBOList, iloList);
        // 保存旧的knowledge_id，为下一步删除question_knowledge_point表做铺垫
        if (iloIds != null && iloIds.size() > 0) {
            List<Long> knowledgeIds = knowledgePointDAO.queryAllKeysByIloId(iloIds);
            questionKnowledgePointDAO.mDeleteQuestKnowlPtByKnowlId(knowledgeIds);
            knowledgePointDAO.mDeleteByIloId(iloIds);
        }
        knowledgePointDAO.mInsertByIloBo(iloBOList);
        // 教学环节结构
        List<TeachStruct> teachStructList = parseTeachStruct(syllabusTableBO.getTeachStructTable(), courseId);
        if (teachStructList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失教学环节结构");
        }
        teachStructDAO.mDeleteByCourseId(courseId);
        teachStructDAO.mInsert(teachStructList);
        // 教学环节细则
        List<TeachDetailBO> teachDetailBOList = parseTeachDetail(syllabusTableBO.getTeachDetailTable(), courseId);
        if (teachDetailBOList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失教学环节细则");
        }
        List<TeachDetail> teachDetailList = teachDetailBo2TeachDetail(teachDetailBOList);
        teachDetailDAO.mDeleteByCourseId(courseId);
        teachDetailDAO.mInsert(teachDetailList);
        // 课程考核结构
        List<AssessStruct> assessStructList = parseAssessStruct(syllabusTableBO.getAssessStructTable(), courseId);
        if (assessStructList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失课程考核结构");
        }
        // 保存旧的assessStructId，为下一步删除ilo_assess_struct表作铺垫
        List<Long> assessStructIds = assessStructDAO.queryAllKeysByCourseId(courseId);
        assessStructDAO.mDeleteByCourseId(courseId);
        assessStructDAO.mInsert(assessStructList);
        // 课程考核细则，这个表并没有用，忽略它
        // 课程考核评估标准
        List<IloAssessStructBO> iloAssessStructBOList = parseIloAssessStruct(syllabusTableBO.getIloAssessStructTables(), courseId);
        if (iloAssessStructBOList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失课程考核评估标准");
        }
        List<IloAssessStruct> iloAssessStructList = iloAssessStructBo2IloAssessStruct(iloAssessStructBOList);
        if ((iloIds != null && iloIds.size() > 0) || (assessStructIds != null && assessStructIds.size() > 0)) {
            iloAssessStructDAO.mDeleteByIloIdOrAssessStructId(iloIds, assessStructIds);
        }
        iloAssessStructDAO.mInsert(iloAssessStructList);
        // 学习进度
        List<CourseSchedule> courseScheduleList = parseCourseSchedule(syllabusTableBO.getCourseScheduleTable(), courseId);
        if (courseScheduleList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失学习进度");
        }
        courseScheduleDAO.mDeleteByCourseId(courseId);
        courseScheduleDAO.mInsert(courseScheduleList);
        // 实验
        List<Experiment> experimentList = parseExperiment(syllabusTableBO.getExperimentTables(), courseId);
        if (experimentList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失实验");
        }
        // 获取实验的备注字段
        parseExperimentSummary(syllabusTableBO.getExperimentSummaryTable(), experimentList);
        experimentDAO.mDeleteByCourseId(courseId);
        experimentDAO.mInsert(experimentList);
        // 实践
        List<Practice> practiceList = parsePractice(syllabusTableBO.getPracticeTables(), courseId);
        if (practiceList == null) {
            throw new InvalidObjectException("文件内容不合法, 缺失实践");
        }
        // 获取实践的备注字段
        parsePracticeSummary(syllabusTableBO.getPracticeSummaryTable(), practiceList);
        practiceDAO.mDeleteByCourseId(courseId);
        practiceDAO.mInsert(practiceList);
    }

    /**
     * 填充IloBO的number字段
     *
     * @param iloBOList 对象列表
     */
    private void processIloBo(List<IloBO> iloBOList) {
        for (IloBO iloBO : iloBOList) {
            // 提取iloDesc中的iloNumber
            iloBO.setIloNumberByDesc();
            // 提取knowledgeDesc中的knowledgeNumber
            iloBO.setKnowledgeNumberByDesc();

            GraduateRequirementL3 l3 = new GraduateRequirementL3(iloBO.getL3Desc());
            List<GraduateRequirementL3> l3List = graduateRequirementL3DAO.queryAll(l3);
            int size = l3List.size();
            // 如果不是一个，那目前的逻辑就有问题
            if (size != 1) {
                throw new IncorrectResultSizeDataAccessException(1, size);
            }
            iloBO.setL3Id(l3List.get(0).getL3Id());
        }
    }

    /**
     * 提取Ilo的number字段
     *
     * @param iloBOList 对象列表
     * @param iloList   对象列表
     */
    private void processIloBo(List<IloBO> iloBOList, List<Ilo> iloList) {
        // iloNumber => ilo的下标
        Map<String, Integer> map = new HashMap<>(iloList.size());
        for (int i = 0; i < iloList.size(); i++) {
            map.put(iloList.get(i).getIloNumber(), i);
        }
        for (IloBO iloBO : iloBOList) {
            int index = map.getOrDefault(iloBO.getIloNumber(), 0);
            iloBO.setIloId(iloList.get(index).getIloId());
        }
    }

    /**
     * 教学大纲对象转数据库对象
     *
     * @param iloBOList 对象列表
     * @return 对象列表
     */
    private List<Ilo> iloBo2Ilo(List<IloBO> iloBOList) {
        List<Ilo> iloList = new ArrayList<>();
        // 用于对ILO进行去重
        Set<String> set = new HashSet<>();
        for (IloBO iloBO : iloBOList) {
            Ilo ilo = new Ilo(iloBO);
            if (set.contains(ilo.getIloNumber())) {
                continue;
            }
            set.add(ilo.getIloNumber());
            // 这里的l3Id已提前填充好
            ilo.setL3Id(iloBO.getL3Id());
            iloList.add(ilo);
        }
        return iloList;
    }

    /**
     * 数据库对象转教学大纲对象
     *
     * @param iloList 对象列表
     * @return 对象列表
     */
    private List<IloBO> ilo2IloBo(List<Ilo> iloList) {
        List<IloBO> iloBOList = new ArrayList<>();
        for (Ilo ilo : iloList) {
            // 获取ILO对应的毕业要求三级
            Long l3Id = ilo.getL3Id();
            GraduateRequirementL3 l3 = graduateRequirementL3DAO.queryById(l3Id);
            Double l3Weight = l3.getL3Weight();
            String l3Desc = l3.getL3Desc();
            // 获取毕业要求三级对应的毕业要求二级
            Long l2Id = l3.getL2Id();
            GraduateRequirementL2 l2 = graduateRequirementL2DAO.queryById(l2Id);
            String l2Desc = l2.getL2Desc();
            // 获取ILO对应的知识点
            Long iloId = ilo.getIloId();
            List<KnowledgePoint> knowledgePointList = knowledgePointDAO.mGetKnowledgePointByIloId(iloId);
            for (KnowledgePoint knowledgePoint : knowledgePointList) {
                IloBO iloBO = new IloBO(knowledgePoint);
                // 设置ILO相关
                iloBO.setInitialLevel(ilo.getInitialLevel());
                iloBO.setAchieveLevel(ilo.getAchieveLevel());
                iloBO.setIloNumber(ilo.getIloNumber());
                iloBO.setIloDesc(ilo.getIloDesc());
                iloBO.setIloWeight(ilo.getIloWeight());
                iloBO.setIloId(iloId);
                // 设置毕业要求三级相关
                iloBO.setL3Id(l3Id);
                iloBO.setL3Desc(l3Desc);
                iloBO.setL3Weight(l3Weight);
                // 设置毕业要求二级相关
                iloBO.setL2Desc(l2Desc);

                iloBOList.add(iloBO);
            }
        }
        return iloBOList;
    }

    /**
     * 教学大纲对象转数据库对象
     *
     * @param teachDetailBOList 对象列表
     * @return 对象列表
     */
    private List<TeachDetail> teachDetailBo2TeachDetail(List<TeachDetailBO> teachDetailBOList) {
        List<TeachDetail> teachDetailList = new ArrayList<>();
        for (TeachDetailBO teachDetailBO : teachDetailBOList) {
            TeachDetail teachDetail = new TeachDetail(teachDetailBO);
            // 获取l2_id
            String l2Desc = teachDetailBO.getL2Desc();
            GraduateRequirementL2 l2 = new GraduateRequirementL2(l2Desc);
            List<GraduateRequirementL2> l2List = graduateRequirementL2DAO.queryAll(l2);
            int size = l2List.size();
            if (size != 1) {
                throw new IncorrectResultSizeDataAccessException(1, size);
            }
            teachDetail.setL2Id(l2List.get(0).getL2Id());
            teachDetailList.add(teachDetail);
        }
        return teachDetailList;
    }

    /**
     * 数据库对象转教学大纲对象
     *
     * @param teachDetailList 对象列表
     * @return 对象列表
     */
    private List<TeachDetailBO> teachDetail2TeachDetailBo(List<TeachDetail> teachDetailList) {
        List<TeachDetailBO> teachDetailBOList = new ArrayList<>();
        for (TeachDetail teachDetail : teachDetailList) {
            TeachDetailBO teachDetailBO = new TeachDetailBO(teachDetail);
            // 获取l2_desc
            Long l2Id = teachDetail.getL2Id();
            GraduateRequirementL2 l2 = graduateRequirementL2DAO.queryById(l2Id);
            teachDetailBO.setL2Desc(l2.getL2Desc());
            teachDetailBOList.add(teachDetailBO);
        }
        return teachDetailBOList;
    }

    /**
     * 教学大纲对象转数据库对象
     *
     * @param iloAssessStructBOList 对象列表
     * @return 对象列表
     */
    private List<IloAssessStruct> iloAssessStructBo2IloAssessStruct(List<IloAssessStructBO> iloAssessStructBOList) {
        List<IloAssessStruct> iloAssessStructList = new ArrayList<>();
        for (IloAssessStructBO iloAssessStructBO : iloAssessStructBOList) {
            IloAssessStruct iloAssessStruct = new IloAssessStruct(iloAssessStructBO);
            // 获取iloId
            Long courseId = iloAssessStructBO.getCourseId();
            Ilo ilo = new Ilo(courseId, iloAssessStructBO.getIloNumber());
            List<Ilo> iloList = iloDAO.queryAll(ilo);
            int size = iloList.size();
            if (size != 1) {
                throw new IncorrectResultSizeDataAccessException(1, size);
            }
            iloAssessStruct.setIloId(iloList.get(0).getIloId());
            // 获取课程考核结构id
            AssessStruct assessStruct = new AssessStruct(courseId, iloAssessStructBO.getAssessStructDesc());
            List<AssessStruct> assessStructList = assessStructDAO.queryAll(assessStruct);
            size = assessStructList.size();
            if (size != 1) {
                throw new IncorrectResultSizeDataAccessException(1, size);
            }
            iloAssessStruct.setAssessStructId(assessStructList.get(0).getAssessStructId());
            iloAssessStructList.add(iloAssessStruct);
        }
        return iloAssessStructList;
    }

    /**
     * 数据库对象转教学大纲对象
     *
     * @param iloAssessStructList 对象列表
     * @return 对象列表
     */
    private List<AssessDetailBO> iloAssessStruct2AssessDetailBo(List<IloAssessStruct> iloAssessStructList) {
        List<AssessDetailBO> assessDetailBOList = new ArrayList<>();
        for (IloAssessStruct iloAssessStruct : iloAssessStructList) {
            AssessDetailBO assessDetailBO = new AssessDetailBO();
            // 获取ILO
            Long iloId = iloAssessStruct.getIloId();
            Ilo ilo = iloDAO.queryById(iloId);
            // 获取ILO对应的毕业要求三级
            Long l3Id = ilo.getL3Id();
            GraduateRequirementL3 l3 = graduateRequirementL3DAO.queryById(l3Id);
            // 获取毕业要求三级对应的毕业要求二级
            Long l2Id = l3.getL2Id();
            GraduateRequirementL2 l2 = graduateRequirementL2DAO.queryById(l2Id);
            // 获取课程考核结构
            Long assessStructId = iloAssessStruct.getAssessStructId();
            AssessStruct assessStruct = assessStructDAO.queryById(assessStructId);
            // 填充字段
            assessDetailBO.setAssessStructDesc(assessStruct.getAssessStructDesc());
            assessDetailBO.setL2Desc(l2.getL2Desc());
            assessDetailBO.setIloNumber(ilo.getIloNumber());
            assessDetailBO.setIloDesc(ilo.getIloDesc());

            assessDetailBOList.add(assessDetailBO);
        }
        // 按照考核项目, l2_desc, ilo_number进行排序
        assessDetailBOList.sort((o1, o2) -> {
            if (!o1.getAssessStructDesc().equals(o2.getAssessStructDesc())) {
                return o1.getAssessStructDesc().compareTo(o2.getAssessStructDesc());
            }
            if (!o1.getL2Desc().equals(o2.getL2Desc())) {
                return o1.getL2Desc().compareTo(o2.getL2Desc());
            }
            return o1.getIloNumber().compareTo(o2.getIloNumber());
        });
        return assessDetailBOList;
    }

    /**
     * 数据库对象转教学大纲对象
     *
     * @param iloAssessStructList 对象列表
     * @return 对象列表
     */
    private Map<String, List<IloAssessStructBO>> iloAssessStruct2IloAssessStructBo(List<IloAssessStruct> iloAssessStructList) {
        Map<String, List<IloAssessStructBO>> listMap = new HashMap<>();
        for (IloAssessStruct iloAssessStruct : iloAssessStructList) {
            IloAssessStructBO iloAssessStructBO = new IloAssessStructBO(iloAssessStruct);
            // 获取ILO
            Long iloId = iloAssessStruct.getIloId();
            Ilo ilo = iloDAO.queryById(iloId);
            // 获取课程考核结构
            Long assessStructId = iloAssessStruct.getAssessStructId();
            AssessStruct assessStruct = assessStructDAO.queryById(assessStructId);
            // 键
            String assessType = assessStruct.getAssessStructDesc();
            // 填充剩余字段
            iloAssessStructBO.setIloNumber(ilo.getIloNumber());
            iloAssessStructBO.setAssessStructDesc(assessType);

            // 放进map
            if (listMap.containsKey(assessType)) {
                listMap.get(assessType).add(iloAssessStructBO);
            } else {
                List<IloAssessStructBO> list = new ArrayList<>();
                list.add(iloAssessStructBO);
                listMap.put(assessType, list);
            }
        }
        return listMap;
    }

    /**
     * 生成教学大纲
     *
     * @param courseId 课程id
     * @return 文件名
     */
    @Override
    public String generateSyllabus(Long courseId) {
        // 创建docx文件
        XWPFDocument docxDocument = SyllabusGenerateUtil.createDocument();

        // 1. 课程基本信息
        Course course = courseDAO.queryById(courseId);
        Curriculum curriculum = curriculumDAO.queryById(course.getCurriculumId());
        SyllabusGenerateUtil.addCourseBasicInfo(docxDocument, course, curriculum);

        // 2. 预期学习结果
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        List<IloBO> iloBOList = ilo2IloBo(iloList);
        SyllabusGenerateUtil.addIntendedLearningOutcomes(docxDocument, iloBOList);

        // 3. 主要教学环节
        List<TeachStruct> teachStructList = teachStructDAO.mGetTeachStructByCourseId(courseId);
        List<TeachDetail> teachDetailList = teachDetailDAO.mGetTeachDetailByCourseId(courseId);
        List<TeachDetailBO> teachDetailBOList = teachDetail2TeachDetailBo(teachDetailList);
        SyllabusGenerateUtil.addTeachingLearningActivities(docxDocument, teachStructList, teachDetailBOList);

        // 4. 课程考核
        List<AssessStruct> assessStructList = assessStructDAO.mGetAssessStructByCourseId(courseId);

        List<Long> assessStructIds = assessStructDAO.queryAllKeysByCourseId(courseId);
        List<IloAssessStruct> iloAssessStructList = iloAssessStructDAO.mGetIloAssessStructByAssessStructIds(assessStructIds);

        List<AssessDetailBO> assessDetailBOList = iloAssessStruct2AssessDetailBo(iloAssessStructList);
        Map<String, List<IloAssessStructBO>> iloAssessStructBoListMap = iloAssessStruct2IloAssessStructBo(iloAssessStructList);
        SyllabusGenerateUtil.addAssessmentScheme(docxDocument, assessStructList, assessDetailBOList, iloAssessStructBoListMap);

        // 5. 学习进度
        List<CourseSchedule> courseScheduleList = courseScheduleDAO.mGetCourseScheduleByCourseId(courseId);
        SyllabusGenerateUtil.addCourseSchedule(docxDocument, courseScheduleList);

        try {
            return SyllabusGenerateUtil.createFile(docxDocument, SYLLABUS, course.getCourseCode());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("生成教学大纲失败");
            return "";
        }
    }

    @Override
    public String viewSyllabus(String pathName, Long courseId) throws IOException {
        String wordPath = String.join(File.separator, ROOT, SYLLABUS, pathName);
        File source = new File(wordPath);

        // 获取当前日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_FOR_FILE);
        String nowStr = sdf.format(date);

        // 创建一个PDF文件
        Course course = courseDAO.queryById(courseId);
        String pdfFileName = SYLLABUS_DOC + "_" + nowStr + "-" + course.getCourseCode() + PDF_FORMAT;
        String pdfPath = String.join(File.separator, ROOT, SYLLABUS, pdfFileName);
        File target = new File(pdfPath);
        if (!target.exists()) {
            if (target.createNewFile()) {
                log.info("创建文件{}成功", pdfPath);
            } else {
                log.info("创建文件{}失败", pdfPath);
            }
        }
        // convert方法指定源文件对象/输入流
        // to方法指定目标文件对象/输出流(源文件可以不存在，如果不存在则会创建)
        // 目标文件的后缀要和即将转换的文件类型一致，否则会造成文件损坏
        // as方法接受的时DocumentFormat对象，我们可以使用默认注册完的DocumentFormat对象，
        // execute方法是执行转换的方法，方法没有返回值，是同步执行
        try {
            documentConverter.convert(source).to(target).as(DefaultDocumentFormatRegistry.PDF).execute();
        } catch (OfficeException e) {
            log.error("文档转换异常:{}", e.getMessage());
        }
        return pdfFileName;
//        FileInputStream fileInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            // 读取docx文件
//            fileInputStream = new FileInputStream(wordPath);
//            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
//            long width = 11907;
//            long height = 16840;
//            CTSectPr sectPr = xwpfDocument.getDocument().getBody().addNewSectPr();
//            CTPageSz pgsz = sectPr.isSetPgSz() ? sectPr.getPgSz() : sectPr.addNewPgSz();
//            pgsz.setW(BigInteger.valueOf(width));
//            pgsz.setH(BigInteger.valueOf(height));
//            CTBody body = xwpfDocument.getDocument().getBody();
//            if (!body.isSetSectPr()) {
//                body.addNewSectPr();
//            }
//            CTSectPr section = body.getSectPr();
//            if(!section.isSetPgSz()) {
//                section.addNewPgSz();
//            }
//            // 设置页面大小  当前A4大小
//            CTPageSz pageSize = section.getPgSz();
//            pageSize.setW(BigInteger.valueOf(11907));
//            pageSize.setH(BigInteger.valueOf(16840));
//            pageSize.setOrient(STPageOrientation.PORTRAIT);
//            PdfOptions pdfOptions = PdfOptions.create();
//            // 输出路径
//            fileOutputStream = new FileOutputStream(pdfPath);
//            PdfConverter.getInstance().convert(xwpfDocument, fileOutputStream, pdfOptions);
//        } catch (Exception e) {
//            log.error("文档转换异常:{}", e.getMessage());
//            e.printStackTrace();
//        } finally {
//            fileInputStream.close();
//            fileOutputStream.close();
//        }
//        return pdfFileName;
//        try {
//            WordprocessingMLPackage pkg = Docx4J.load(new File(wordPath));
//
//            Mapper fontMapper = new IdentityPlusMapper();
//            fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
//            pkg.setFontMapper(fontMapper);
//
//            Docx4J.toPDF(pkg, new FileOutputStream(pdfPath));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Docx4JException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return pdfFileName;
    }
}
