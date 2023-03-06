package com.bylan.dcybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 教师端首页获取课程达成度的DTO
 *
 * @author Rememorio
 * @date 2022-04-17 22:44
 */
@ApiModel("教师端首页获取课程达成度的DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeCourseReachDTO implements Serializable {

    @NotEmpty(message = "课程id列表不能为空")
    @ApiModelProperty("课程id列表")
    @JsonProperty("course_ids")
    List<Long> courseIds;

}
