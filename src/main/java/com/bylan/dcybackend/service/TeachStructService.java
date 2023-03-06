package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.CreateTeachStructDTO;
import com.bylan.dcybackend.dto.UpdateTeachStructDTO;
import com.bylan.dcybackend.vo.TeachStructVO;

import java.util.List;

/**
 * 教学大纲的教学环节结构服务
 *
 * @author Rememorio
 */
public interface TeachStructService {

    /**
     * 通过课程id获取教学环节结构
     *
     * @param courseId 课程id
     * @return 教学环节结构列表
     */
    List<TeachStructVO> getTeachStruct(Long courseId);

    /**
     * 批量删除
     *
     * @param teachStructIds 教学环节结构id列表
     * @return 成功
     */
    Boolean mDeleteTeachStruct(List<Long> teachStructIds);

    /**
     * 批量更新
     *
     * @param updateTeachStructDTOList 更新教学环节结构DTO
     * @return 成功
     */
    Boolean mUpdateTeachStruct(List<UpdateTeachStructDTO> updateTeachStructDTOList);

    /**
     * 插入单个
     *
     * @param createTeachStructDTO 新建教学环节结构DTO
     * @return 成功
     */
    Boolean insertTeachStruct(CreateTeachStructDTO createTeachStructDTO);

}
