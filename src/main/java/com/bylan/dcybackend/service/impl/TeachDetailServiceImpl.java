package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.GraduateRequirementL1DAO;
import com.bylan.dcybackend.dao.GraduateRequirementL2DAO;
import com.bylan.dcybackend.dao.TeachDetailDAO;
import com.bylan.dcybackend.dto.CreateTeachDetailDTO;
import com.bylan.dcybackend.dto.UpdateTeachDetailDTO;
import com.bylan.dcybackend.entity.GraduateRequirementL1;
import com.bylan.dcybackend.entity.GraduateRequirementL2;
import com.bylan.dcybackend.entity.TeachDetail;
import com.bylan.dcybackend.service.TeachDetailService;
import com.bylan.dcybackend.vo.TeachDetailVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 教学大纲的教学环节细则服务实现
 *
 * @author Rememorio
 */
@Service
public class TeachDetailServiceImpl implements TeachDetailService {

    private static final Logger log = LogManager.getLogger(TeachDetailServiceImpl.class);

    @Autowired
    TeachDetailDAO teachDetailDAO;

    @Autowired
    GraduateRequirementL2DAO graduateRequirementL2DAO;

    @Autowired
    GraduateRequirementL1DAO graduateRequirementL1DAO;

    /**
     * 通过课程id获取教学环节细则
     *
     * @param courseId 课程id
     * @return 教学环节细则列表
     */
    @Override
    public List<TeachDetailVO> getTeachDetail(Long courseId) {
        List<TeachDetail> teachDetailList = teachDetailDAO.mGetTeachDetailByCourseId(courseId);
        List<TeachDetailVO> teachDetailVOList = new ArrayList<>(teachDetailList.size());
        for (TeachDetail teachDetail : teachDetailList) {
            TeachDetailVO teachDetailVO = new TeachDetailVO(teachDetail);
            // 获取毕业要求二级
            Long l2Id = teachDetailVO.getL2Id();
            GraduateRequirementL2 l2 = graduateRequirementL2DAO.queryById(l2Id);
            teachDetailVO.setL2Desc(l2.getL2Desc());
            // 获取毕业要求一级
            Long l1Id = l2.getL1Id();
            GraduateRequirementL1 l1 = graduateRequirementL1DAO.queryById(l1Id);
            teachDetailVO.setL1Id(l1Id);
            teachDetailVO.setL1Desc(l1.getL1Desc());
            teachDetailVOList.add(teachDetailVO);
        }
        // 按照l1_desc, l2_desc排序
        teachDetailVOList.sort((o1, o2) -> {
            if (!o1.getL1Desc().equals(o2.getL1Desc())) {
                return o1.getL1Desc().compareTo(o2.getL1Desc());
            }
            return o1.getL2Desc().compareTo(o2.getL2Desc());
        });
        return teachDetailVOList;
    }

    /**
     * 批量删除
     *
     * @param teachDetailIds 教学环节细则id列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeleteTeachDetail(List<Long> teachDetailIds) {
        try {
            teachDetailDAO.mDeleteById(teachDetailIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除teach_detail时失败 堆栈信息Ids: {}; 数据信息:{}", e, teachDetailIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateTeachDetailDTOList 更新教学环节细则DTO
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdateTeachDetail(List<UpdateTeachDetailDTO> updateTeachDetailDTOList) {
        try {
            teachDetailDAO.mUpdate(updateTeachDetailDTOList);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新teach_detail时失败 堆栈信息: {}; 数据信息: {}", e, updateTeachDetailDTOList);
            return false;
        }
        return true;
    }

    /**
     * 插入单个
     *
     * @param createTeachDetailDTO 新建教学环节细则DTO
     * @return 成功
     */
    @Override
    public Boolean insertTeachDetail(CreateTeachDetailDTO createTeachDetailDTO) {
        try {
            TeachDetail teachDetail = new TeachDetail(createTeachDetailDTO);
            teachDetailDAO.insert(teachDetail);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入teach_detail时失败 堆栈信息: {}; 数据信息: {}", e, createTeachDetailDTO);
            return false;
        }
        return true;
    }
}
