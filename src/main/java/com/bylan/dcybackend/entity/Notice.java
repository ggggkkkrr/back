package com.bylan.dcybackend.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 公告
 *
 * @author Rememorio
 */
@ApiModel("公告")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @ApiModelProperty("公告id")
    private Long noticeId;
    @ApiModelProperty("教学班id")
    private Long sectionId;
    @ApiModelProperty("公告内容")
    private String noticeContent;
    @ApiModelProperty("元组创建时间")
    private Date createTime;
    @ApiModelProperty("元组修改时间")
    private Date modifyTime;

}
