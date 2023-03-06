package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.CreateExperimentDTO;
import com.bylan.dcybackend.dto.UpdateExperimentDTO;
import com.bylan.dcybackend.vo.ExperimentVO;

import java.util.List;

/**
 * 教学大纲的实验服务
 *
 * @author Rememorio
 */
public interface ExperimentService {

    /**
     * 通过课程id获取实验
     *
     * @param courseId 课程id
     * @return 实验列表
     */
    List<ExperimentVO> getExperiment(Long courseId);

    /**
     * 批量删除
     *
     * @param experimentIds 实验id列表
     * @return 成功
     */
    Boolean mDeleteExperiment(List<Long> experimentIds);

    /**
     * 批量更新
     *
     * @param updateExperimentDTOList 实验列表
     * @return 成功
     */
    Boolean mUpdateExperiment(List<UpdateExperimentDTO> updateExperimentDTOList);

    /**
     * 插入单个
     *
     * @param createExperimentDTO 新增实验DTO
     * @return 成功
     */
    Boolean insertExperiment(CreateExperimentDTO createExperimentDTO);

}
