package com.bylan.dcybackend.component;

import com.alibaba.fastjson.JSON;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.MessageDTO;
import com.bylan.dcybackend.entity.ChatMessage;
import com.bylan.dcybackend.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhuaming
 * @date 2022/3/29 9:45
 */
@ServerEndpoint("/imserver/{sectionId}/{userId}")
@Component
public class WebSocketServer {

    static final Logger log = LogManager.getLogger(WebSocketServer.class);


    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<Long, ConcurrentHashMap<String, WebSocketServer>> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

    private Long sectionId;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ConfigurableApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName, @PathParam("sectionId") Long sectionId, @PathParam("userId") String userId) {

        log.error("用户:" + userId + ",开始连接!!!!!!");
        this.session = session;
        this.userId = userId;
        this.sectionId = sectionId;
        ConcurrentHashMap<String, WebSocketServer> map = webSocketMap.get(sectionId);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            webSocketMap.put(sectionId, map);
        }
        map.put(userId, this);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent("连接成功");
        messageDTO.setType(1);
        try {
            sendMessage(JSON.toJSONString(messageDTO));
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        ConcurrentHashMap<String, WebSocketServer> map = webSocketMap.get(sectionId);
        if (map != null && map.containsKey(userId)) {
            map.remove(userId);
        }
        log.info("用户退出:" + userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message massage
     * @param session session
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("用户消息:" + userId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                MessageDTO messageDTO = JSON.parseObject(message, MessageDTO.class);

                if (messageDTO.getType() == null) {
                    return;
                }

                // 服务器
                ChatService chatService = applicationContext.getBean(ChatService.class);

                if (messageDTO.getType().equals(Constant.MessageType.SEND_PERSON_MSG)) {
                    // 应该交到数据库落盘
                    messageDTO.setSectionId(sectionId);
                    ChatMessage chatMessage = new ChatMessage(messageDTO.getMessageType(),messageDTO.getContent(), messageDTO.getFromId(), messageDTO.getFromName(), messageDTO.getToId(), sectionId,messageDTO.getCreateTime());
                    chatService.addMessage(chatMessage);
                    messageDTO.setId(chatMessage.getMessageId());
                    log.info("回填后id{}", messageDTO.getId());
                    // 广播给队友
                    messageDTO.setType(Constant.MessageType.BROADCAST_PERSON_MSG);
                    // 个人转发消息 只需要转发给to_id就行
                    if (StringUtils.isNotBlank(messageDTO.getContent()) && webSocketMap.get(sectionId).containsKey(messageDTO.getToId())) {
                        webSocketMap.get(sectionId).get(messageDTO.getToId()).sendMessage(JSON.toJSONString(messageDTO));
                    } else {
                        log.error("请求的userId:" + messageDTO.getToId() + "不在该服务器上");
                        //否则不在这个服务器上，发送到mysql或者redis
                    }
                    // 回显给自己
                    messageDTO.setType(Constant.MessageType.ECHO);
                    webSocketMap.get(sectionId).get(userId).sendMessage(JSON.toJSONString(messageDTO));
                } else if (messageDTO.getType().equals(Constant.MessageType.SEND_GROUP_MSG)) {
                    // 处理群聊消息
                    // 应该交到数据库落盘
                    messageDTO.setSectionId(sectionId);
                    ChatMessage chatMessage = new ChatMessage(messageDTO.getMessageType(),messageDTO.getContent(), messageDTO.getFromId(), messageDTO.getFromName(), messageDTO.getToId(), sectionId,messageDTO.getCreateTime());
                    chatService.addMessage(chatMessage);
                    messageDTO.setId(chatMessage.getMessageId());
                    log.info("回填后id{}", messageDTO.getId());
                    // 广播给队友
                    messageDTO.setType(Constant.MessageType.BROADCAST_GROUP_MSG);
                    ConcurrentHashMap<String, WebSocketServer> map = webSocketMap.get(sectionId);
                    map.forEach((itemId, socket) -> {
                        if (!Objects.equals(userId, itemId)) {
                            // 自己不转发
                            try {
                                socket.sendMessage(JSON.toJSONString(messageDTO));
                            } catch (IOException e) {
                                log.error("请求的userId:" + itemId + "不在该服务器上");
                            }
                        }
                    });
                    // 回显消息
                    messageDTO.setType(Constant.MessageType.ECHO);
                    map.get(userId).sendMessage(JSON.toJSONString(messageDTO));
                } else if (messageDTO.getType().equals(Constant.MessageType.LEAVE)) {
                    chatService.updateReadTime(sectionId, messageDTO.getFromId(), messageDTO.getToId(), messageDTO.getCreateTime());
                } else if (messageDTO.getType().equals(Constant.MessageType.BACKOUT_PERSON_MSG)) {
                    log.info("撤回消息id：{} fromId{} userId{}", messageDTO.getId(), messageDTO.getFromId(), userId);

                    // 撤回自己的消息
                    chatService.deleteMessage(messageDTO.getId(), Constant.Chat.DELETE_BY_OWNER);

                    // 发给自己
                    webSocketMap.get(sectionId).get(userId).sendMessage(JSON.toJSONString(messageDTO));
                    if (webSocketMap.get(sectionId).containsKey(messageDTO.getToId())) {
                        // 发给对方
                        webSocketMap.get(sectionId).get(messageDTO.getToId()).sendMessage(JSON.toJSONString(messageDTO));
                    } else {
                        log.error("请求的userId:" + messageDTO.getToId() + "不在该服务器上");
                        //否则不在这个服务器上，发送到mysql或者redis
                    }
                } else if (messageDTO.getType().equals(Constant.MessageType.BACKOUT_GROUP_MSG)) {
                    log.info("撤回消息id：{} fromId{} userId{}", messageDTO.getId(), messageDTO.getFromId(), userId);
                    if (messageDTO.getFromId().equals(userId)) {
                        // 撤回自己的消息
                        chatService.deleteMessage(messageDTO.getId(), Constant.Chat.DELETE_BY_OWNER);
                    } else {
                        // 管理员撤回消息
                        chatService.deleteMessage(messageDTO.getId(), Constant.Chat.DELETE_BY_ADMIN);
                    }
                    // 通知所有人撤回
                    ConcurrentHashMap<String, WebSocketServer> map = webSocketMap.get(sectionId);
                    map.forEach((itemId, socket) -> {
                        try {
                            socket.sendMessage(JSON.toJSONString(messageDTO));
                        } catch (IOException e) {
                            log.error("请求的userId:" + itemId + "不在该服务器上");
                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 错误时执行
     *
     * @param session session
     * @param error   error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        log.info("发送者{} 消息{}", this.userId, message);
        this.session.getBasicRemote().sendText(message);
    }


//    /**
//     * 发送自定义消息
//     * */
//    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
//        log.info("发送消息到:"+userId+"，报文:"+message);
//        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
//            webSocketMap.get(userId).sendMessage(message);
//        }else{
//            log.error("用户"+userId+",不在线！");
//        }
//    }
}
