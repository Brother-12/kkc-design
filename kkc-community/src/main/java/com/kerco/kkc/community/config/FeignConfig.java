package com.kerco.kkc.community.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 解决Feign客户端，不能携带原先请求的cookie问题
 *
 * 解决Feign异步模式下不是一个线程不能获取RequestContextHolder问题
 *
 * 当我们不使用异步编排的时候也就是单线程执行的时候，请求上下文持有器即：
 *  RequestContextHolder采用的是ThreadLocal存储请求对象。当我们采用异步编排时，而是多个线程去执行，新建的线程会丢失请求对象。
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)
                        RequestContextHolder.getRequestAttributes();

                //不同线程获取RequestContextHolder时 会出现NullPointerException
                HttpServletRequest request = servletRequestAttributes.getRequest();

                //将原请求携带的cookie，在feign客户端调用时也携带
                String cookie = request.getHeader("token");

                requestTemplate.header("token",cookie);
            }
        };
    }
}
