package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.ExperimentDAO;
import com.bylan.dcybackend.dto.CreateExperimentDTO;
import com.bylan.dcybackend.dto.UpdateExperimentDTO;
import com.bylan.dcybackend.entity.Experiment;
import com.bylan.dcybackend.service.ExperimentService;
import com.bylan.dcybackend.vo.ExperimentVO;
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
public class ExperimentServiceImpl implements ExperimentService {

    private static final Logger log = LogManager.getLogger(ExperimentServiceImpl.class);

    @Autowired
    ExperimentDAO experimentDAO;

    /**
     * 通过课程id获取实验列表
     *
     * @param courseId 课程id
     * @return 实验列表
     */
    @Override
    public List<ExperimentVO> getExperiment(Long courseId) {
        List<Experiment> experimentList = experimentDAO.mGetExperimentByCourseId(courseId);
        List<ExperimentVO> experimentVOList = new ArrayList<>(experimentList.size());
        for (Experiment experiment : experimentList) {
            ExperimentVO experimentVO = new ExperimentVO(experiment);
            experimentVOList.add(experimentVO);
        }
        return experimentVOList;
    }

    /**
     * 批量删除
     *
     * @param experimentIds 实验id列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeleteExperiment(List<Long> experimentIds) {
        try {
            experimentDAO.mDeleteById(experimentIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除experiment时失败 堆栈信息Ids: {}; 数据信息: {}", e, experimentIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateExperimentDTOList 实验列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdateExperiment(List<UpdateExperimentDTO> updateExperimentDTOList) {
        try {
            experimentDAO.mUpdate(updateExperimentDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新experiment时失败 堆栈信息: {}; 数据信息: {}", e, updateExperimentDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createExperimentDTO 新增实验DTO
     * @return 成功
     */
    @Override
    public Boolean insertExperiment(CreateExperimentDTO createExperimentDTO) {
        try {
            Experiment experiment = new Experiment(createExperimentDTO);
            experimentDAO.insert(experiment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入experiment时失败 堆栈信息: {}; 数据信息: {}", e, createExperimentDTO);
            return false;
        }
        return true;
    }
}
