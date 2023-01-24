package com.kerco.kkc.member.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 用户关键信息类
 *
 */
@ToString
@Data
public class UserInfoVo {

    private Long id;
    private String username;
    private String avatar;
}
