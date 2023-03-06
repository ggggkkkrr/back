package com.bylan.dcybackend.netty;

import com.alibaba.fastjson.JSON;
import com.bylan.dcybackend.boot.ApplicationContextUtil;
import com.bylan.dcybackend.domain.Constant;
import com.bylan.dcybackend.dto.MessageDTO;
import com.bylan.dcybackend.entity.ChatMessage;
import com.bylan.dcybackend.enums.MsgActionEnum;
import com.bylan.dcybackend.service.ChatService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

/**
 * @author wuhuaming
 * @date 2022/06/04 17:12
 */
@Slf4j


public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private ChatService chatService = ApplicationContextUtil.getBean(ChatService.class);
    private ChannelUtils channelUtils = (ChannelUtils) ApplicationContextUtil.getBean("channelUtils");

    public static volatile ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private WebSocketServerHandshaker handshaker;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接首先进入 =====================");
        log.info("有链接进入{}",1);
        log.info("{}",ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("连接之后进入 =====================");
        log.info("判断{}",evt instanceof WebSocketServerProtocolHandler.HandshakeComplete);
        //websocket握手成功触发
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            users.add(ctx.channel());
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete= (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String s = handshakeComplete.requestUri();
            System.out.println(ctx.channel().id().asLongText() + "连接ffff成功,web客户端数量：" + users.size() + "个"+s);
        }
        super.userEventTriggered(ctx,evt);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有新的连接.>>当前连接数量:"+users.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        //不用手动一处，ChannelGroup可以自动从分组里移除ctx
        System.out.println(ctx.channel().id().asLongText() + "连接断开,剩余web客户端数量：" + users.size() + "个");
        log.info("断开连接==============");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("收到服务器端消息:{}",msg);
        String content = msg.text();
//        String content = "a";
        log.info("收到来自频道 {} 的消息: {}", ctx.channel().id(), content);
        Channel currentChannel = ctx.channel();
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        log.info("收到反序列后的对象:{}",dataContent);
        Integer action = dataContent.getAction();
        MsgActionEnum actionEnum = MsgActionEnum.getEnumByType(action);
        log.info("执行操作:{}",actionEnum);
        assert actionEnum != null;
        ChatMsg chatMsg = dataContent.getChatMsg();
        DataContent responseData;
        switch (actionEnum) {
            case CONNECT:
                // 2.1 当 websocket 第一次 open 的时候，初始化channel，把 channel 和用户的唯一id关联
                UserChannelRel.add(chatMsg.getSectionId(), chatMsg.getFromId(), currentChannel);
                // 测试
                for (Channel c : users) {
                    System.out.println(c.id().asLongText());
                }
                UserChannelRel.outPut();
                break;
            case SEND_PERSON_MSG:
                // 2.2 聊天类型的消息，把聊天记录保存到数据库
                // 保存消息到数据库
                ChatMessage chatMessage = new ChatMessage(chatMsg.getMessageType(),chatMsg.getContent(), chatMsg.getFromId(), chatMsg.getFromName(), chatMsg.getToId(), chatMsg.getSectionId(),chatMsg.getCreateTime());
                chatService.addMessage(chatMessage);
                // 主键回填
                chatMsg.setId(chatMessage.getMessageId());
                // 发送消息

                responseData = new DataContent();
                responseData.setAction(MsgActionEnum.BROADCAST_MSG.type);
                responseData.setChatMsg(chatMsg);
                // 发给好友
                channelUtils.sendMessageToUser(chatMsg.getToId(), chatMsg.getSectionId(), responseData);
                // 发给自己
                channelUtils.sendMessageToUser(chatMsg.getFromId(), chatMsg.getSectionId(), responseData);
                break;
            case SEND_GROUP_MSG:
                // 2.2 聊天类型的消息，把聊天记录保存到数据库
                // 保存消息到数据库
                chatMessage = new ChatMessage(chatMsg.getMessageType(),chatMsg.getContent(), chatMsg.getFromId(), chatMsg.getFromName(), chatMsg.getToId(), chatMsg.getSectionId(),chatMsg.getCreateTime());
                chatService.addMessage(chatMessage);
                // 主键回填
                chatMsg.setId(chatMessage.getMessageId());
                // 发送消息

                responseData = new DataContent();
                responseData.setAction(MsgActionEnum.BROADCAST_MSG.type);
                responseData.setChatMsg(chatMsg);
                // 发给好友
                channelUtils.sendMessageToGroupUser(chatMsg.getSectionId(), responseData);
                break;

            case LEAVE:
                chatService.updateReadTime(chatMsg.getSectionId(), chatMsg.getFromId(), chatMsg.getToId(), chatMsg.getCreateTime());
                break;
            case BACKOUT_PERSON_MSG:
                chatService.deleteMessage(chatMsg.getId(), Constant.Chat.DELETE_BY_OWNER);

                // 发给自己
                responseData = new DataContent();
                responseData.setAction(MsgActionEnum.BROADCAST_BACKOUT.type);
                responseData.setChatMsg(chatMsg);
                channelUtils.sendMessageToUser(chatMsg.getFromId(),chatMsg.getSectionId(),responseData);
                channelUtils.sendMessageToUser(chatMsg.getToId(),chatMsg.getSectionId(),responseData);
                break;
            case BACKOUT_GROUP_MSG:
                chatService.deleteMessage(chatMsg.getId(), chatMsg.getMessageType());
                responseData = new DataContent();
                responseData.setAction(MsgActionEnum.BROADCAST_BACKOUT.type);
                responseData.setChatMsg(chatMsg);
                channelUtils.sendMessageToGroupUser(chatMsg.getSectionId(),responseData);
                break;
            case DISCONNECT: {
                UserChannelRel.remove(chatMsg.getSectionId(), chatMsg.getFromId());
                UserChannelRel.outPut();
            }
//            case SIGNED:
//                // 2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
//                // 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
//                String msgIdStr = dataContent.getChatMsg().getMsgId();
//                String[] msgIds = msgIdStr.split(",");
//                List<Integer> msgIdList = Arrays.stream(msgIds).filter(StringUtils::isNoneBlank)
//                        .map(Integer::parseInt).collect(Collectors.toList());
//                if (!msgIdList.isEmpty()) {
//                    //批量签收
//                    chatMessageService.updateMsgSigned(msgIdList);
//                }
//                break;
//            case GROUP_CHAT:
//                // 群聊天
//                ChatMsg groupMsg = dataContent.getChatMsg();
//                Integer groupMsgId = groupMessageService.saveMessage(groupMsg);
//                String groupNickname = groupService.getUserGroupNickname(groupMsg.getGroupId(), senderId);
//                groupMsg.setNickname(groupNickname);
////                String name = userService.getUserInfo(senderId).getName();
////                groupMsg.setName(name);
//                dataContent.setChatMsg(groupMsg);
//                channelUtils.sendMessageToGroupUser(senderId, groupMsg.getGroupId(), dataContent);
//                break;
//            case KEEPALIVE:
//                // 2.4 心跳类型的消息
//                System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
//                break;
            default:
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

}
