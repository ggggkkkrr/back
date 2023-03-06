package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.CreateTeachDetailDTO;
import com.bylan.dcybackend.dto.UpdateTeachDetailDTO;
import com.bylan.dcybackend.vo.TeachDetailVO;

import java.util.List;

/**
 * 教学大纲的教学环节细则服务
 *
 * @author Rememorio
 */
public interface TeachDetailService {

    /**
     * 通过课程id获取教学环节细则
     *
     * @param courseId 课程id
     * @return 教学环节细则列表
     */
    List<TeachDetailVO> getTeachDetail(Long courseId);

    /**
     * 批量删除
     *
     * @param teachDetailIds 教学环节细则id列表
     * @return 成功
     */
    Boolean mDeleteTeachDetail(List<Long> teachDetailIds);

    /**
     * 批量更新
     *
     * @param updateTeachDetailDTOList 更新教学环节细则DTO
     * @return 成功
     */
    Boolean mUpdateTeachDetail(List<UpdateTeachDetailDTO> updateTeachDetailDTOList);

    /**
     * 插入单个
     *
     * @param createTeachDetailDTO 新建教学环节细则DTO
     * @return 成功
     */
    Boolean insertTeachDetail(CreateTeachDetailDTO createTeachDetailDTO);

}
