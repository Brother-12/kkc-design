package com.kerco.kkc.member.service;

import com.kerco.kkc.member.entity.vo.UserAuthVo;
import com.kerco.kkc.member.entity.vo.UserRegisterVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AccessService {

    /**
     * 用户认证
     * @param authVo 用户发送的验证信息
     * @param request HttpServletRequest
     * @return token
     */
    String authentication(UserAuthVo authVo, HttpServletRequest request);

    Map<String, Object> getUserInfo(HttpServletRequest request);

    /**
     * 退出登录
     * @param request HttpServletRequest
     */
    void logout(HttpServletRequest request);

    /**
     * 注册用户
     * @param userRegisterVo 待注册的信息
     */
    String registerUser(UserRegisterVo userRegisterVo);
}
