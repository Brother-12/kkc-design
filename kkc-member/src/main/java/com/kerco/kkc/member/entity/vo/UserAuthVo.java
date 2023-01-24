package com.kerco.kkc.member.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 用户认证类：接收前端发送的 用户名、密码、验证码
 */
@ToString
@Data
public class UserAuthVo {

    private String username;

    private String password;

    private String code;
}
