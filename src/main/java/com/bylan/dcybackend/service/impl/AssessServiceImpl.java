package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.AssessStructDAO;
import com.bylan.dcybackend.dao.IloAssessStructDAO;
import com.bylan.dcybackend.dao.IloDAO;
import com.bylan.dcybackend.dto.*;
import com.bylan.dcybackend.entity.AssessStruct;
import com.bylan.dcybackend.entity.Ilo;
import com.bylan.dcybackend.entity.IloAssessStruct;
import com.bylan.dcybackend.service.AssessService;
import com.bylan.dcybackend.vo.AssessStructVO;
import com.bylan.dcybackend.vo.IloAssessStructVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 教学大纲的课程考核服务实现
 *
 * @author Rememorio
 */
@Service
public class AssessServiceImpl implements AssessService {

    private static final Logger log = LogManager.getLogger(AssessServiceImpl.class);

    @Autowired
    IloDAO iloDAO;

    @Autowired
    AssessStructDAO assessStructDAO;

    @Autowired
    IloAssessStructDAO iloAssessStructDAO;

    /**
     * 通过课程id获取课程考核结构
     *
     * @param courseId 课程id
     * @return 课程考核结构列表
     */
    @Override
    public List<AssessStructVO> getAssessStruct(Long courseId) {
        List<AssessStruct> assessStructList = assessStructDAO.mGetAssessStructByCourseId(courseId);
        List<AssessStructVO> assessStructVOList = new ArrayList<>(assessStructList.size());
        for (AssessStruct assessStruct : assessStructList) {
            AssessStructVO assessStructVO = new AssessStructVO(assessStruct);
            assessStructVOList.add(assessStructVO);
        }
        return assessStructVOList;
    }

    /**
     * 批量删除
     *
     * @param assessStructIds 课程考核结构id列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeleteAssessStruct(List<Long> assessStructIds) {
        try {
            assessStructDAO.mDeleteById(assessStructIds);
            iloAssessStructDAO.mDeleteByIloIdOrAssessStructId(null, assessStructIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除assess_struct时失败 堆栈信息Ids: {}; 数据信息:{}", e, assessStructIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateAssessStructDTOList 更新课程考核结构DTO
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdateAssessStruct(List<UpdateAssessStructDTO> updateAssessStructDTOList) {
        try {
            assessStructDAO.mUpdate(updateAssessStructDTOList);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新assess_struct时失败 堆栈信息: {}; 数据信息: {}", e, updateAssessStructDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createAssessStructDTO 新建课程考核结构DTO
     * @return 成功
     */
    @Override
    public Boolean insertAssessStruct(CreateAssessStructDTO createAssessStructDTO) {
        try {
            AssessStruct assessStruct = new AssessStruct(createAssessStructDTO);
            assessStructDAO.insert(assessStruct);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入assess_struct时失败 堆栈信息: {}; 数据信息: {}", e, createAssessStructDTO);
            return false;
        }
        return true;
    }

    /**
     * 通过课程id获取ilo-课程考核结构
     *
     * @param assessStructId 课程id
     * @return ilo-课程考核结构列表
     */
    @Override
    public List<IloAssessStructVO> getIloAssessStruct(Long assessStructId) {
        List<IloAssessStruct> iloAssessStructList = iloAssessStructDAO.mGetIloAssessStructByAssessStructId(assessStructId);
        List<IloAssessStructVO> iloAssessStructVOList = new ArrayList<>(iloAssessStructList.size());
        // 获取课程考核结构字段，因为是同一个课程考核结构，可以复用
        AssessStruct assessStruct = assessStructDAO.queryById(assessStructId);
        for (IloAssessStruct iloAssessStruct : iloAssessStructList) {
            IloAssessStructVO iloAssessStructVO = new IloAssessStructVO(iloAssessStruct);
            iloAssessStructVO.setAssessStructDesc(assessStruct.getAssessStructDesc());
            iloAssessStructVO.setAssessStructWeight(assessStruct.getAssessStructWeight());
            // 获取预期学习结果字段
            Long iloId = iloAssessStruct.getIloId();
            Ilo ilo = iloDAO.queryById(iloId);
            iloAssessStructVO.setIloDesc(ilo.getIloDesc());
            iloAssessStructVO.setIloNumber(ilo.getIloNumber());
            iloAssessStructVOList.add(iloAssessStructVO);
        }
        // 按照ilo_number排序
        iloAssessStructVOList.sort(Comparator.comparing(IloAssessStructVO::getIloNumber));
        return iloAssessStructVOList;
    }

    /**
     * 批量删除
     *
     * @param deleteIloAssessStructDTOList 删除预期学习结果-课程考核结构DTO
     * @return 成功
     */
    @Override
    public Boolean mDeleteIloAssessStruct(List<DeleteIloAssessStructDTO> deleteIloAssessStructDTOList) {
        try {
            iloAssessStructDAO.mDeleteById(deleteIloAssessStructDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除ilo_assess_struct时失败 堆栈信息Ids: {}; 数据信息:{}", e, iloAssessStructDAO);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateIloAssessStructDTOList 更新预期学习结果-课程考核结构DTO
     * @return 成功
     */
    @Override
    public Boolean mUpdateIloAssessStruct(List<UpdateIloAssessStructDTO> updateIloAssessStructDTOList) {
        // 数据库没有设置外键，所以需要先保证数据合法
        for (UpdateIloAssessStructDTO updateIloAssessStructDTO : updateIloAssessStructDTOList) {
            Ilo ilo = iloDAO.queryById(updateIloAssessStructDTO.getIloId());
            if (ilo == null) {
                log.warn("更新失败 updateIloAssessStructDTO: {}的iloId不合法", updateIloAssessStructDTO);
                return false;
            }
            AssessStruct assessStruct = assessStructDAO.queryById(updateIloAssessStructDTO.getAssessStructId());
            if (assessStruct == null) {
                log.warn("更新失败 updateIloAssessStructDTO: {}的assessStructId不合法", updateIloAssessStructDTO);
                return false;
            }
        }

        try {
            iloAssessStructDAO.mUpdate(updateIloAssessStructDTOList);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新ilo_assess_struct时失败 堆栈信息: {}; 数据信息: {}", e, updateIloAssessStructDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createIloAssessStructDTO 创建预期学习结果-课程考核结构DTO
     * @return 成功
     */
    @Override
    public Boolean insertIloAssessStruct(CreateIloAssessStructDTO createIloAssessStructDTO) {
        // 数据库没有设置外键，所以需要先保证数据合法
        Ilo ilo = iloDAO.queryById(createIloAssessStructDTO.getIloId());
        if (ilo == null) {
            log.warn("插入失败 createIloAssessStructDTO: {}的iloId不合法", createIloAssessStructDTO);
            return false;
        }
        AssessStruct assessStruct = assessStructDAO.queryById(createIloAssessStructDTO.getAssessStructId());
        if (assessStruct == null) {
            log.warn("插入失败 createIloAssessStructDTO: {}的assessStructId不合法", createIloAssessStructDTO);
            return false;
        }

        try {
            IloAssessStruct iloAssessStruct = new IloAssessStruct(createIloAssessStructDTO);
            iloAssessStructDAO.insert(iloAssessStruct);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入ilo_assess_struct时失败 堆栈信息: {}; 数据信息: {}", e, createIloAssessStructDTO);
            return false;
        }
        return true;
    }
}
