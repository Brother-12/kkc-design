package com.kerco.kkc.member.config;

import com.kerco.kkc.member.interceptor.LoginInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册Interceptor拦截器(Interceptor这个类是我们自己写的拦截器类)
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        //addPathPatterns()方法添加需要拦截的路径
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //excludePathPatterns()方法添加不拦截的路径
        //添加不拦截路径
        registration.excludePathPatterns(
                //登录
                "/access/login",
                //获取验证码
                "/access/code",
                //注册
                "/access/register",
                //管理员登录
                "/access/login/admin111",
                //获取评论的用户信息
                "/member/user/getUserList",
                //获取用户的简单信息
                "/user/show/getUser",
                //获取用户的关注列表
                "/user/follow/list",
                //获取用户的粉丝列表
                "/user/followed/list",
                //html静态资源
                "/**/*.html",
                //js静态资源
                "/**/*.js",
                //css静态资源
                "/**/*.css"
        );
    }
}
