package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.vo.AssessStructVO;
import com.bylan.dcybackend.vo.IloAssessStructVO;

import java.util.List;

/**
 * 教学大纲的课程考核服务
 *
 * @author Rememorio
 */
public interface AssessService {

    /**
     * 通过课程id获取课程考核结构
     *
     * @param courseId 课程id
     * @return 课程考核结构列表
     */
    List<AssessStructVO> getAssessStruct(Long courseId);

    /**
     * 批量删除
     *
     * @param assessStructIds 课程考核结构id列表
     * @return 成功
     */
    Boolean mDeleteAssessStruct(List<Long> assessStructIds);

    /**
     * 批量更新
     *
     * @param updateAssessStructDTOList 更新课程考核结构DTO
     * @return 成功
     */
    Boolean mUpdateAssessStruct(List<UpdateAssessStructDTO> updateAssessStructDTOList);

    /**
     * 插入单个
     *
     * @param createAssessStructDTO 新建课程考核结构DTO
     * @return 成功
     */
    Boolean insertAssessStruct(CreateAssessStructDTO createAssessStructDTO);

    /**
     * 通过assessStructId获取ilo-课程考核结构
     *
     * @param assessStructId 课程考核结构id
     * @return ilo-课程考核结构列表
     */
    List<IloAssessStructVO> getIloAssessStruct(Long assessStructId);

    /**
     * 批量删除
     *
     * @param deleteIloAssessStructDTOList 删除预期学习结果-课程考核结构DTO
     * @return 成功
     */
    Boolean mDeleteIloAssessStruct(List<DeleteIloAssessStructDTO> deleteIloAssessStructDTOList);

    /**
     * 批量更新
     *
     * @param updateIloAssessStructDTOList 更新预期学习结果-课程考核结构DTO
     * @return 成功
     */
    Boolean mUpdateIloAssessStruct(List<UpdateIloAssessStructDTO> updateIloAssessStructDTOList);

    /**
     * 插入单个
     *
     * @param createIloAssessStructDTO 创建预期学习结果-课程考核结构DTO
     * @return 成功
     */
    Boolean insertIloAssessStruct(CreateIloAssessStructDTO createIloAssessStructDTO);

}
