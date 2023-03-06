package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.TeachStructDAO;
import com.bylan.dcybackend.dto.CreateTeachStructDTO;
import com.bylan.dcybackend.dto.UpdateTeachStructDTO;
import com.bylan.dcybackend.entity.TeachStruct;
import com.bylan.dcybackend.service.TeachStructService;
import com.bylan.dcybackend.vo.TeachStructVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 教学大纲的教学环节结构服务实现
 *
 * @author Rememorio
 */
@Service
public class TeachStructServiceImpl implements TeachStructService {

    private static final Logger log = LogManager.getLogger(TeachStructServiceImpl.class);

    @Autowired
    TeachStructDAO teachStructDAO;

    /**
     * 通过课程id获取教学环节结构
     *
     * @param courseId 课程id
     * @return 教学环节结构列表
     */
    @Override
    public List<TeachStructVO> getTeachStruct(Long courseId) {
        List<TeachStruct> teachStructList = teachStructDAO.mGetTeachStructByCourseId(courseId);
        List<TeachStructVO> teachStructVOList = new ArrayList<>(teachStructList.size());
        for (TeachStruct teachStruct : teachStructList) {
            TeachStructVO teachStructVO = new TeachStructVO(teachStruct);
            teachStructVOList.add(teachStructVO);
        }
        return teachStructVOList;
    }

    /**
     * 批量删除
     *
     * @param teachStructIds 教学环节结构id列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeleteTeachStruct(List<Long> teachStructIds) {
        try {
            teachStructDAO.mDeleteById(teachStructIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除teach_struct时失败 堆栈信息Ids: {}; 数据信息:{}", e, teachStructIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateTeachStructDTOList 更新教学环节结构DTO
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdateTeachStruct(List<UpdateTeachStructDTO> updateTeachStructDTOList) {
        try {
            teachStructDAO.mUpdate(updateTeachStructDTOList);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新teach_struct时失败 堆栈信息: {}; 数据信息: {}", e, updateTeachStructDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createTeachStructDTO 新建教学环节结构DTO
     * @return 成功
     */
    @Override
    public Boolean insertTeachStruct(CreateTeachStructDTO createTeachStructDTO) {
        try {
            TeachStruct teachStruct = new TeachStruct(createTeachStructDTO);
            teachStructDAO.insert(teachStruct);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入teach_struct时失败 堆栈信息: {}; 数据信息: {}", e, createTeachStructDTO);
            return false;
        }
        return true;
    }
}
