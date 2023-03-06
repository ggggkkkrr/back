package com.bylan.dcybackend.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author wuhuaming
 * @date 2022/4/1 22:15
 */
public interface ChatMessageStateDAO {

    /**
     * 获取读取时间
     *
     * @param sectionId 教学班id
     * @param fromId    fromId
     * @param toId      toId
     * @return 读取时间
     */
    Date getReadTimeOfPerson(@Param("sectionId") Long sectionId,
                             @Param("fromId") String fromId,
                             @Param("toId") String toId);

    /**
     * 获取群读取时间
     *
     * @param sectionId 教学班id
     * @param userId    用户id
     * @return 读取时间
     */
    Date getGroupReadTime(@Param("sectionId") Long sectionId,
                          @Param("userId") String userId);

    /**
     * 更新读取时间
     *
     * @param sectionId 教学班id
     * @param fromId    fromId
     * @param toId      toId
     * @param readTime  读取时时间
     */
    void updateReadTime(@Param("sectionId") Long sectionId,
                        @Param("fromId") String fromId,
                        @Param("toId") String toId,
                        @Param("readTime") Date readTime);


}
