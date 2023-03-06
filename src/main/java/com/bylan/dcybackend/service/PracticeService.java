package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.CreatePracticeDTO;
import com.bylan.dcybackend.dto.UpdatePracticeDTO;
import com.bylan.dcybackend.vo.PracticeVO;

import java.util.List;

/**
 * 教学大纲的实践服务
 *
 * @author Rememorio
 */
public interface PracticeService {

    /**
     * 通过课程id获取实践
     *
     * @param courseId 课程id
     * @return 实践列表
     */
    List<PracticeVO> getPractice(Long courseId);

    /**
     * 批量删除
     *
     * @param practiceIds 实践id列表
     * @return 成功
     */
    Boolean mDeletePractice(List<Long> practiceIds);

    /**
     * 批量更新
     *
     * @param updatePracticeDTOList 实践列表
     * @return 成功
     */
    Boolean mUpdatePractice(List<UpdatePracticeDTO> updatePracticeDTOList);

    /**
     * 插入单个
     *
     * @param createPracticeDTO 新增实践DTO
     * @return 成功
     */
    Boolean insertPractice(CreatePracticeDTO createPracticeDTO);

}
