package com.kerco.kkc.member.entity.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 前台 关注类 VO
 */
@ToString
@Data
public class FollowVo {

//    用户id
    @NotNull(message = "userId不能为空")
    private Long userId;
//    要关注的人的用户id
    @NotNull(message = "followId不能为空")
    private Long followId;
}
