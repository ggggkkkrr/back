package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.dto.CreateDiscussionDTO;
import com.bylan.dcybackend.vo.DiscussionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * (Discussion)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:58:32
 */
public interface DiscussionDAO {

    /**
     * 新增一级评论
     *
     * @param createDiscussionDTO 新增评论DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int addFirstDiscussion(CreateDiscussionDTO createDiscussionDTO) throws DataAccessException;

    /**
     * 新增回复评论
     *
     * @param createDiscussionDTO 创建评论DTO
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int addReplyDiscussion(CreateDiscussionDTO createDiscussionDTO) throws DataAccessException;

    /**
     * 更改评论状态 可见或者不可见
     *
     * @param discussionIds 评论ids
     * @param status        状态
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int updateDiscussionStatus(@Param("discussionIds") List<Long> discussionIds, @Param("status") Long status) throws DataAccessException;

    /**
     * 更新评论内容
     *
     * @param discussionId 评论id
     * @param content      内容
     * @return 影响行数
     * @throws DataAccessException 数据库异常
     */
    int updateDiscussionContent(@Param("discussionId") Long discussionId, @Param("content") String content) throws DataAccessException;

    /**
     * 获取所有评论
     *
     * @param sectionId 教学班id
     * @return 评论VO
     * @throws DataAccessException 数据库异常
     */
    List<DiscussionVO> getDiscussionByCourseId(@Param("courseId") Long sectionId);

}