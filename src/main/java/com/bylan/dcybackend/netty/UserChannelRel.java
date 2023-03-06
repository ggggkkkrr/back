package com.bylan.dcybackend.netty;

import com.alibaba.fastjson.JSON;
import com.bylan.dcybackend.component.WebSocketServer;
import com.bylan.dcybackend.dto.MessageDTO;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuhuaming
 * @date 2022/06/04 17:45
 */
@Slf4j
public class UserChannelRel {
    // sectionId, userId, channel
    private static ConcurrentHashMap<Long, ConcurrentHashMap<String, Channel>> manager = new ConcurrentHashMap<>();

    public static void add(Long sectionId,String userId, Channel channel) {
        ConcurrentHashMap<String, Channel> map = manager.get(sectionId);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            manager.put(sectionId, map);
        }
        map.put(userId, channel);
    }

    public static Channel get(Long sectionId, String userId) {
        return manager.get(sectionId).get(userId);
    }

    public static ConcurrentHashMap<String,Channel> getGroup(Long sectionId) {
        return manager.get(sectionId);
    }

    public static void remove(Long sectionId, String userId) {
        manager.get(sectionId).remove(userId);
    }

    public static void outPut() {
        for (Map.Entry<Long, ConcurrentHashMap<String, Channel>> section : manager.entrySet()) {
            for (Map.Entry<String, Channel> entry : section.getValue().entrySet()) {
                log.info("用户：{} 教学班：{} 通道：{}",entry.getKey(),section.getKey(),entry.getValue().id().asLongText());
            }
        }
    }
}
