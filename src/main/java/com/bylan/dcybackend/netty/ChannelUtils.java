package com.bylan.dcybackend.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhuaming
 * @date 2022/06/04 17:17
 */
@Component("channelUtils")
public class ChannelUtils {
    public void sendMessageToUser(String toId, Long sectionId, DataContent dataContent) {
        Channel receiverChannel = UserChannelRel.get(sectionId,toId);
        if (receiverChannel == null) {
            // 用户离线消息
//            this.sendOfflineMessage();
        } else {
            Channel findChannel = ChatHandler.users.find(receiverChannel.id());
            if (findChannel != null) {
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContent)));
            } else {
                // 用户离线消息
//                this.sendOfflineMessage();
            }
        }
    }

    public void sendMessageToGroupUser(Long sectionId, DataContent dataContent) {
        ConcurrentHashMap<String,Channel> map = UserChannelRel.getGroup(sectionId);
        map.forEach((userId,channel) -> {
            Channel findChannel = ChatHandler.users.find(channel.id());
            if (findChannel != null) {
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContent)));
            } else {
                // 用户离线消息
//                this.sendOfflineMessage();
            }
        });

    }
}
