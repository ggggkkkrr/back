package com.bylan.dcybackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuhuaming
 * @date 2022/06/04 17:30
 */
@AllArgsConstructor
@Getter
public enum MsgActionEnum {

    CONNECT(1, "第一次(或重连)初始化连接"),
    SEND_PERSON_MSG(2,"前端给后端发私人消息"),
    SEND_GROUP_MSG(3,"前端给后端发群聊消息"),
    BACKOUT_PERSON_MSG(4,"撤销个人消息"),
    BACKOUT_GROUP_MSG(5,"撤销群聊消息"),
    LEAVE(6,"已读"),

    BROADCAST_MSG(7,"广播消息"),
    BROADCAST_BACKOUT(8,"广播撤回操作"),
    DISCONNECT(9,"断开连接"),
//    CHAT(2, "聊天消息"),
//    SIGNED(3, "消息签收"),
//    KEEPALIVE(4, "客户端保持心跳"),
//    PULL_FRIEND(5, "拉取好友"),
//    NEW_FRIEND(6, "好友申请通过"),
//    NEW_GROUP_MEMBER(7, "新成员加群"),
//    DISBAND_GROUP(8, "解散群"),
//    REQUEST_FRIEND(9, "通知有好友发起申请"),
//    GROUP_CHAT(10, "群聊天消息"),
    GROUP_NOTICE(11, "群公告");

//    public static final Integer SEND_PERSON_MSG = 1; // 前端向后端发私人聊天消息
//    public static final Integer SEND_GROUP_MSG = 2; // 前端像后端发送的群聊天消息
//    public static final Integer ECHO = 3; // 回显
//    public static final Integer BROADCAST_PERSON_MSG = 4; // 广播私人消息
//    public static final Integer BROADCAST_GROUP_MSG = 5; // 广播群聊消息
//    public static final Integer LEAVE = 6; // 已读
//    public static final Integer BACKOUT_PERSON_MSG = 7; // 撤销消息
//    public static final Integer BACKOUT_GROUP_MSG = 8; // 撤销消息

    public final Integer type;
    public final String value;


    public static MsgActionEnum getEnumByType(Integer type) {
        for (MsgActionEnum actionEnum : MsgActionEnum.class.getEnumConstants()) {
            if (actionEnum.getType().equals(type)) {
                return actionEnum;
            }
        }
        return null;
    }
}
