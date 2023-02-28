package com.kerco.kkc.community.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.community.entity.to.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static ThreadLocal<Map<String, Object>> _toThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //1.第一步，验证是否有携带token
        if(StringUtils.hasLength(token)){
            //2.判断token是否是有效
            if(JwtUtils.checkToken(token)){
                //3.判断是否有到过期时间
                Date expiration = JwtUtils.parseJwt(token).getExpiration();
                if(expiration.after(new Date())){
                    //4. 还可以在做一个，与redis的token进行比较，相同则放行，不同则抛异常
                    _toThreadLocal.set(JwtUtils.getPayLoadALSOExcludeExpAndIat(token));

                    //5. 也可以在token中去验证登陆ip与请求的ip是否一致
                    return true;
                }
            }
        }
        CommonResult<Object> error = CommonResult.error(1001, "登陆凭证过期，请重新登陆");
        response.getWriter().write(objectMapper.writeValueAsString(error));
        return false;
    }
}
