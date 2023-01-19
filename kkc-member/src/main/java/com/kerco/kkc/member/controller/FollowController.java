package com.kerco.kkc.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * 默认的话 配置文件发生了变化，但是本地应用没有生效
 * 加了@RefreshScope：刷新配置文件并且重新赋值
 *
 *
 * @author kerco
 * @since 2023-01-12
 */
@RefreshScope
@Api(value="FollowController",tags = {"关注信息控制器"})
@RestController
@RequestMapping("/member/follow")
public class FollowController {

    @Value("${custom.hello}")
    private String value;

    @ApiOperation("获取用户的关注")
    @GetMapping("/getUserFollow")
    public String getUserFollow(){
        return "asdasd";
    }

    @GetMapping("/hello")
    public Map getHello(){
        System.out.println(value);
        Map<String, String> getenv = System.getenv();
        return getenv;
    }
}
