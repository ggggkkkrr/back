package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.PracticeDAO;
import com.bylan.dcybackend.dto.CreatePracticeDTO;
import com.bylan.dcybackend.dto.UpdatePracticeDTO;
import com.bylan.dcybackend.entity.Practice;
import com.bylan.dcybackend.service.PracticeService;
import com.bylan.dcybackend.vo.PracticeVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 实验服务实现
 *
 * @author Rememorio
 */
@Service
public class PracticeServiceImpl implements PracticeService {

    private static final Logger log = LogManager.getLogger(PracticeServiceImpl.class);

    @Autowired
    PracticeDAO practiceDAO;

    /**
     * 通过课程id获取实践
     *
     * @param courseId 课程id
     * @return 实践列表
     */
    @Override
    public List<PracticeVO> getPractice(Long courseId) {
        List<Practice> practiceList = practiceDAO.mGetPracticeByCourseId(courseId);
        List<PracticeVO> practiceVOList = new ArrayList<>(practiceList.size());
        for (Practice practice : practiceList) {
            PracticeVO practiceVO = new PracticeVO(practice);
            practiceVOList.add(practiceVO);
        }
        return practiceVOList;
    }

    /**
     * 批量删除
     *
     * @param practiceIds 实践id列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeletePractice(List<Long> practiceIds) {
        try {
            practiceDAO.mDeleteById(practiceIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除practice时失败 堆栈信息Ids: {}; 插入数据信息: {}", e, practiceIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updatePracticeDTOList 实践列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdatePractice(List<UpdatePracticeDTO> updatePracticeDTOList) {
        try {
            practiceDAO.mUpdate(updatePracticeDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新practice时失败 堆栈信息: {}; 插入数据信息: {}", e, updatePracticeDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createPracticeDTO 新增实践DTO
     * @return 成功
     */
    @Override
    public Boolean insertPractice(CreatePracticeDTO createPracticeDTO) {
        try {
            Practice practice = new Practice(createPracticeDTO);
            practiceDAO.insert(practice);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入practice时失败 堆栈信息: {}; 插入数据信息: {}", e, createPracticeDTO);
            return false;
        }
        return true;
    }
}