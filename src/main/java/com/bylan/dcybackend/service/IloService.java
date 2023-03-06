package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.CreateIloDTO;
import com.bylan.dcybackend.dto.UpdateIloDTO;
import com.bylan.dcybackend.vo.IloVO;
import com.bylan.dcybackend.vo.L1VO;
import com.bylan.dcybackend.vo.L2VO;
import com.bylan.dcybackend.vo.L3VO;

import java.util.List;

/**
 * 教学大纲的ILO服务
 *
 * @author Rememorio
 */
public interface IloService {

    /**
     * 通过courseId获取ilo
     *
     * @param courseId 课程id
     * @return ilo列表
     */
    List<IloVO> mGetIloByCourseId(Long courseId);

    /**
     * 批量删除
     *
     * @param iloIds iloId列表
     * @return 成功
     */
    Boolean mDeleteIlo(List<Long> iloIds);

    /**
     * 批量更新
     *
     * @param updateIloDTOList ilo列表
     * @return 成功
     */
    Boolean mUpdateIlo(List<UpdateIloDTO> updateIloDTOList);

    /**
     * 通过专业id获取毕业要求一级
     *
     * @param majorId 专业id
     * @return l1列表
     */
    List<L1VO> getL1(Long majorId);

    /**
     * 通过毕业要求一级Id获取毕业要求二级
     *
     * @param l1Id l1Id
     * @return l2列表
     */
    List<L2VO> getL2(Long l1Id);

    /**
     * 通过毕业要求二级Id获取毕业要求三级
     *
     * @param l2Id l2Id
     * @return l3列表
     */
    List<L3VO> getL3(Long l2Id);

    /**
     * 插入单个
     *
     * @param createIloDTO 新增ILO的DTO
     * @return 成功
     */
    Boolean insertIlo(CreateIloDTO createIloDTO);

}
