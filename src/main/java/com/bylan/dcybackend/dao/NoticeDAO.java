package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.CreateNoticeDTO;
import com.bylan.dcybackend.vo.NoticeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Notice)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:33
 */
public interface NoticeDAO {

    /**
     * 新增公告
     *
     * @param notice 公告
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int addNotice(CreateNoticeDTO notice) throws DataAccessException;

    /**
     * 删除公告
     *
     * @param noticeIds 公告id
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int deleteNotice(@Param("noticeIds") List<Long> noticeIds) throws DataAccessException;

    /**
     * 通过教学班id获取公告
     *
     * @param sectionId 教学班id
     * @return 公告
     */
    List<NoticeVO> getNoticeBySectionId(@Param("sectionId") Long sectionId);

}