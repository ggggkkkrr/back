package com.bylan.dcybackend.dao;

import com.bylan.dcybackend.entity.ChatMessage;
import com.bylan.dcybackend.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/4/1 15:26
 */
public interface ChatMessageDAO {

    /**
     * 插入记录
     *
     * @param chatMessage 消息
     */
    void createChatMessage(ChatMessage chatMessage);

    /**
     * 获取群历史记录
     *
     * @param sectionId  教学班id
     * @param fromId     fromId
     * @param toId       toId
     * @param startMsgId 开始消息id
     * @param size       数量
     * @return 消息
     */
    List<MessageVO> getGroupHistory(@Param("sectionId") Long sectionId,
                                    @Param("fromId") String fromId,
                                    @Param("toId") String toId,
                                    @Param("startMsgId") Long startMsgId,
                                    @Param("size") Integer size);

    /**
     * 获取好友历史记录
     *
     * @param sectionId  教学班id
     * @param fromId     fromId
     * @param toId       toId
     * @param startMsgId 开始消息id
     * @param size       数量
     * @return 消息
     */
    List<MessageVO> getFriendHistory(@Param("sectionId") Long sectionId,
                                     @Param("fromId") String fromId,
                                     @Param("toId") String toId,
                                     @Param("startMsgId") Long startMsgId,
                                     @Param("size") Integer size);

    /**
     * 获取个人消息数量
     *
     * @param sectionId 教学班id
     * @param fromId    fromId
     * @param toId      toId
     * @param readTime  读取时间
     * @return 数量
     */
    Integer getPersonMsgNum(@Param("sectionId") Long sectionId,
                            @Param("fromId") String fromId,
                            @Param("toId") String toId,
                            @Param("readTime") Date readTime);

    /**
     * 获取群消息数量
     *
     * @param sectionId     教学班id
     * @param groupReadTime 群读取时间
     * @return 数量
     */
    Integer getGroupMsgNum(@Param("sectionId") Long sectionId,
                           @Param("groupReadTime") Date groupReadTime);

    /**
     * 删除消息
     *
     * @param id     消息id
     * @param status 状态
     */
    void deleteMessage(@Param("messageId") Long id,
                       @Param("status") Integer status);
}