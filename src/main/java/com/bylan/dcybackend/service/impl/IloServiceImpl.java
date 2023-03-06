package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.*;
import com.bylan.dcybackend.dto.CreateIloDTO;
import com.bylan.dcybackend.dto.UpdateIloDTO;
import com.bylan.dcybackend.entity.GraduateRequirementL1;
import com.bylan.dcybackend.entity.GraduateRequirementL2;
import com.bylan.dcybackend.entity.GraduateRequirementL3;
import com.bylan.dcybackend.entity.Ilo;
import com.bylan.dcybackend.service.IloService;
import com.bylan.dcybackend.vo.IloVO;
import com.bylan.dcybackend.vo.L1VO;
import com.bylan.dcybackend.vo.L2VO;
import com.bylan.dcybackend.vo.L3VO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 教学大纲的ILO服务实现
 *
 * @author Rememorio
 */
@Service
public class IloServiceImpl implements IloService {

    private static final Logger log = LogManager.getLogger(IloServiceImpl.class);

    @Autowired
    IloDAO iloDAO;

    @Autowired
    IloAssessStructDAO iloAssessStructDAO;

    @Autowired
    GraduateRequirementL1DAO graduateRequirementL1DAO;

    @Autowired
    GraduateRequirementL2DAO graduateRequirementL2DAO;

    @Autowired
    GraduateRequirementL3DAO graduateRequirementL3DAO;


    /**
     * 通过courseId获取ilo
     *
     * @param courseId 课程id
     * @return ilo列表
     */
    @Override
    public List<IloVO> mGetIloByCourseId(Long courseId) {
        List<Ilo> iloList = iloDAO.mGetIloByCourseId(courseId);
        List<IloVO> iloVOList = new ArrayList<>();
        for (Ilo ilo : iloList) {
            IloVO iloVO = new IloVO(ilo);
            Long l3Id = ilo.getL3Id();
            // 获取毕业要求三级
            GraduateRequirementL3 l3 = graduateRequirementL3DAO.queryById(l3Id);
            iloVO.setL3Id(l3Id);
            iloVO.setL3Desc(l3.getL3Desc());
            // 获取毕业要求二级
            Long l2Id = l3.getL2Id();
            GraduateRequirementL2 l2 = graduateRequirementL2DAO.queryById(l2Id);
            iloVO.setL2Id(l2Id);
            iloVO.setL2Desc(l2.getL2Desc());
            // 获取毕业要求一级
            Long l1Id = l2.getL1Id();
            GraduateRequirementL1 l1 = graduateRequirementL1DAO.queryById(l1Id);
            iloVO.setL1Id(l1Id);
            iloVO.setL1Desc(l1.getL1Desc());
            iloVOList.add(iloVO);
        }
        // 按照l1_desc, l2_desc, l3_desc, ilo_number排序
        iloVOList.sort((o1, o2) -> {
            // 自定义比较器，小于0 降序，大于0 升序
            if (!o1.getL1Desc().equals(o2.getL1Desc())) {
                return o1.getL1Desc().compareTo(o2.getL1Desc());
            }
            if (!o1.getL2Desc().equals(o2.getL2Desc())) {
                return o1.getL2Desc().compareTo(o2.getL2Desc());
            }
            if (!o1.getL3Desc().equals(o2.getL3Desc())) {
                return o1.getL3Desc().compareTo(o2.getL3Desc());
            }
            return o1.getIloNumber().compareTo(o2.getIloNumber());
        });
        return iloVOList;
    }

    /**
     * 通过iloId批量删除
     *
     * @param iloIds iloId列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mDeleteIlo(List<Long> iloIds) {
        try {
            // 删除与该ILO有关的表，可能还有漏网之鱼
            iloAssessStructDAO.mDeleteByIloIdOrAssessStructId(iloIds, null);
            iloDAO.mDeleteById(iloIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("删除ilo时失败 堆栈信息Ids: {}; 数据信息: {}", e, iloIds);
            return false;
        }
        return true;
    }

    /**
     * 批量更新
     *
     * @param updateIloDTOList ilo列表
     * @return 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mUpdateIlo(List<UpdateIloDTO> updateIloDTOList) {
        try {
            iloDAO.mUpdate(updateIloDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新ilo时失败 堆栈信息: {}; 数据信息: {}", e, updateIloDTOList);
            return false;
        }
        return true;
    }

    /**
     * 通过专业id获取毕业要求一级
     *
     * @param majorId 专业id
     * @return l1列表
     */
    @Override
    public List<L1VO> getL1(Long majorId) {
        List<GraduateRequirementL1> l1List = graduateRequirementL1DAO.queryByMajorId(majorId);
        List<L1VO> l1VOList = new ArrayList<>();
        for (GraduateRequirementL1 l1 : l1List) {
            L1VO l1VO = new L1VO(l1);
            l1VOList.add(l1VO);
        }
        return l1VOList;
    }

    /**
     * 通过毕业要求一级Id获取毕业要求二级
     *
     * @param l1Id l1Id
     * @return l2列表
     */
    @Override
    public List<L2VO> getL2(Long l1Id) {
        List<GraduateRequirementL2> l2List = graduateRequirementL2DAO.queryByL1Id(l1Id);
        List<L2VO> l2VOList = new ArrayList<>();
        for (GraduateRequirementL2 l2 : l2List) {
            L2VO l2VO = new L2VO(l2);
            l2VOList.add(l2VO);
        }
        return l2VOList;
    }

    /**
     * 通过毕业要求二级Id获取毕业要求三级
     *
     * @param l2Id l2Id
     * @return l3列表
     */
    @Override
    public List<L3VO> getL3(Long l2Id) {
        List<GraduateRequirementL3> l3List = graduateRequirementL3DAO.queryByL2Id(l2Id);
        List<L3VO> l3VOList = new ArrayList<>();
        for (GraduateRequirementL3 l3 : l3List) {
            L3VO l3VO = new L3VO(l3);
            l3VOList.add(l3VO);
        }
        return l3VOList;
    }

    /**
     * 插入单个
     *
     * @param createIloDTO 新增ILO的DTO
     * @return 成功
     */
    @Override
    public Boolean insertIlo(CreateIloDTO createIloDTO) {
        try {
            Ilo ilo = new Ilo(createIloDTO);
            iloDAO.insert(ilo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入ilo时失败 堆栈信息: {}; 数据信息: {}", e, createIloDTO);
            return false;
        }
        return true;
    }
}
