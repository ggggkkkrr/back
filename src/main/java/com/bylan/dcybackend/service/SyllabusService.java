package com.bylan.dcybackend.service;

import com.bylan.dcybackend.bo.SyllabusTableBO;
import com.bylan.dcybackend.dto.UpdateCourseDTO;
import com.bylan.dcybackend.vo.CourseVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * 教学大纲服务
 *
 * @author Rememorio
 */
public interface SyllabusService {

    /**
     * 更新单个课程
     *
     * @param updateCourseDTO 更新课程DTO
     * @return 成功
     */
    Boolean updateCourse(UpdateCourseDTO updateCourseDTO);

    /**
     * 获取单个课程
     *
     * @param courseId 课程id
     * @return 课程
     */
    CourseVO getCourseByCourseId(Long courseId);

    /**
     * 下载大纲模板
     *
     * @param response 响应
     */
    void downloadTemplate(HttpServletResponse response);

    /**
     * 上传教学大纲
     *
     * @param file 文件
     * @return 上传之后的文件名
     * @throws IOException 文件名异常
     */
    String uploadSyllabus(MultipartFile file) throws IOException;

    /**
     * 根据文件名删除已经存在的文件
     *
     * @param filename 文件名
     * @return 成功
     */
    Boolean deleteSyllabusFile(String filename);

    /**
     * 下载教学大纲
     *
     * @param filename 文件名
     * @param response 响应
     */
    void downloadSyllabus(String filename, HttpServletResponse response);

    /**
     * 解析教学大纲
     *
     * @param file     文件
     * @param courseId 课程id
     * @throws IOException 数据库异常
     */
    void parseSyllabus(MultipartFile file, Long courseId) throws IOException;

    /**
     * 访问数据库
     *
     * @param syllabusTableBO 教学大纲表格
     * @param courseId        课程id
     * @throws InvalidObjectException 文件内容不合法异常
     */
    void accessDatabase(SyllabusTableBO syllabusTableBO, Long courseId) throws InvalidObjectException;

    /**
     * 生成教学大纲
     *
     * @param courseId 课程id
     * @return 文件名
     */
    String generateSyllabus(Long courseId);

    /**
     * 查看教学大纲
     *
     * @param pathName 教学大纲word文件的路径
     * @return 文件名
     */
    String viewSyllabus(String pathName, Long courseId) throws IOException;

}
