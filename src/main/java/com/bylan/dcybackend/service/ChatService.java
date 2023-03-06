package com.bylan.dcybackend.service;

import com.bylan.dcybackend.dto.MessageDTO;
import com.bylan.dcybackend.entity.ChatMessage;
import com.bylan.dcybackend.vo.FriendMsgStatusVO;
import com.bylan.dcybackend.vo.MessageVO;
import com.bylan.dcybackend.vo.UserVO;

import java.util.Date;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/31 22:23
 */
public interface ChatService {
    /**
     * 通过userId获取姓名 user可能是老师也可能是学生
     *
     * @param userId 用户id
     * @return 姓名
     */
    String getUserNameById(String userId);

    /**
     * 获取这教学班的所有同学和老师
     *
     * @param sectionId 教学班id
     * @return 用户VO
     */
    List<UserVO> getFriendListBySectionId(Long sectionId);

    /**
     * 新增消息
     *
     * @param chatMessage
     */
    void addMessage(ChatMessage chatMessage);

    /**
     * 获取聊天历史记录
     *
     * @param sectionId  教学班id
     * @param fromId     fromId
     * @param toId       toId
     * @param startMsgId 开始消息的id
     * @return 消息VO
     */
    List<MessageVO> getChatHistory(Long sectionId, String fromId, String toId, Long startMsgId);

    /**
     * 获取还有消息状态
     *
     * @param sectionId 教学班id
     * @param studentId 学生id
     * @return 好友消息状态
     */
    List<FriendMsgStatusVO> getFriendMsgStatus(Long sectionId, String studentId);

    /**
     * 更新读取时间
     *
     * @param sectionId 教学班id
     * @param fromId    fromId
     * @param toId      toId
     * @param readTime  读取时间
     */
    void updateReadTime(Long sectionId, String fromId, String toId, Date readTime);

    /**
     * 删除消息
     *
     * @param id            消息id
     * @param deleteByOwner 消息状态
     */
    void deleteMessage(Long id, Integer deleteByOwner);
}
