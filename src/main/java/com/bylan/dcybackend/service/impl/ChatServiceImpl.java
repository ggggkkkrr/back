package com.bylan.dcybackend.service.impl;

import com.bylan.dcybackend.dao.*;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.MessageDTO;
import com.bylan.dcybackend.entity.ChatMessage;
import com.bylan.dcybackend.service.ChatService;
import com.bylan.dcybackend.vo.FriendMsgStatusVO;
import com.bylan.dcybackend.vo.MessageVO;
import com.bylan.dcybackend.vo.UserVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author wuhuaming
 * @date 2022/3/31 22:26
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger log = LogManager.getLogger(ChatServiceImpl.class);

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private SectionStudentDAO sectionStudentDAO;

    @Autowired
    private SectionTeacherDAO sectionTeacherDAO;

    @Autowired
    private ChatMessageDAO chatMessageDAO;

    @Autowired
    private ChatMessageStateDAO chatMessageStateDAO;

    @Override
    public String getUserNameById(String userId) {
        String stuName = studentDAO.getNameById(userId);
        String teacherName = teacherDAO.getNameById(userId);
        if (stuName != null) {
            return stuName;
        } else {
            return teacherName;
        }
    }

    @Override
    public List<UserVO> getFriendListBySectionId(Long sectionId) {
        List<UserVO> studentList = sectionStudentDAO.getStuUserVoBySectionId(sectionId);
        studentList.forEach(item ->
                item.setType(Constant.Chat.STUDENT)
        );
        List<UserVO> teacherList = sectionTeacherDAO.getTeaUserVoBySectionId(sectionId);
        teacherList.forEach(item ->
                item.setType(Constant.Chat.TEACHER)
        );
        List<UserVO> temp = new ArrayList<>();
        temp.add(new UserVO("0", "班群", Constant.Chat.GROUP));
        temp.addAll(teacherList);
        temp.addAll(studentList);
        return temp;
    }

    @Override
    public void addMessage(ChatMessage chatMessage) {
        chatMessageDAO.createChatMessage(chatMessage);
        chatMessageStateDAO.updateReadTime(chatMessage.getSectionId(),
                chatMessage.getFromId(),
                chatMessage.getToId(),
                chatMessage.getCreateTime());
    }

    @Override
    public List<MessageVO> getChatHistory(Long sectionId, String fromId, String toId, Long startMsgId) {
        List<MessageVO> messageVOList;
        Integer size;
        if (startMsgId == null) {
            size = Constant.Chat.INIT_PAGE_SIZE;
        } else {
            size = Constant.Chat.PAGE_SIZE;
        }
        log.info("getChatHistory 参数{} {} {}", sectionId, fromId, toId);
        // 判断是否是群聊
        if ("0".equals(toId)) {
            // 群聊
            log.info("群聊天消息 接收方{}", fromId);
            messageVOList = chatMessageDAO.getGroupHistory(sectionId, fromId, toId, startMsgId, size);
        } else {
            messageVOList = chatMessageDAO.getFriendHistory(sectionId, fromId, toId, startMsgId, size);
        }
        messageVOList.forEach(item -> {
            if (item.getMessageStatus().equals(Constant.Chat.DELETE_BY_OWNER)) {
                item.setContent("已被" + item.getFromName() + "撤回");
            } else if (item.getMessageStatus().equals(Constant.Chat.DELETE_BY_ADMIN)) {
                item.setContent("已被管理员撤回");
            }
        });
        Collections.sort(messageVOList);
        return messageVOList;
    }

    @Override
    public List<FriendMsgStatusVO> getFriendMsgStatus(Long sectionId, String userId) {
        List<FriendMsgStatusVO> list1 = sectionStudentDAO.getStuIdBySectionId(sectionId, userId);
        List<FriendMsgStatusVO> list2 = sectionTeacherDAO.getTeaIdBySectionId(sectionId, userId);
        List<FriendMsgStatusVO> friendMsgStatusVOList = new ArrayList<>();
        friendMsgStatusVOList.addAll(list1);
        friendMsgStatusVOList.addAll(list2);
        friendMsgStatusVOList.forEach((item) -> {
            Date readTime = chatMessageStateDAO.getReadTimeOfPerson(sectionId, item.getUserId(), userId);
            Integer num = chatMessageDAO.getPersonMsgNum(sectionId, item.getUserId(), userId, readTime);
            item.setStatus(num > 0);
        });
        // 获取群聊消息
        Date groupReadTime = chatMessageStateDAO.getGroupReadTime(sectionId, userId);
        Integer num = chatMessageDAO.getGroupMsgNum(sectionId, groupReadTime);
        friendMsgStatusVOList.add(new FriendMsgStatusVO("0", num > 0));
        return friendMsgStatusVOList;
    }

    @Override
    public void updateReadTime(Long sectionId, String fromId, String toId, Date readTime) {
        chatMessageStateDAO.updateReadTime(sectionId, fromId, toId, readTime);
    }

    @Override
    public void deleteMessage(Long id, Integer status) {
        chatMessageDAO.deleteMessage(id, status);
    }


}
