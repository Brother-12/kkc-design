package com.kerco.kkc.member.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kerco.kkc.common.constant.RedisConstant;
import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.member.entity.User;
import com.kerco.kkc.member.entity.vo.UserAuthVo;
import com.kerco.kkc.member.entity.vo.UserRegisterVo;
import com.kerco.kkc.member.mapper.UserMapper;
import com.kerco.kkc.member.service.AccessService;
import com.kerco.kkc.member.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户认证
     * @param authVo 用户发送的验证信息
     * @param request HttpServletRequest
     * @return token
     */
    @Override
    public String authentication(UserAuthVo authVo, HttpServletRequest request) {
        //1.去数据库校验前先校验
        // 1)先判断验证码不能为空
        if(Strings.isNullOrEmpty(authVo.getCode())){
            throw new RuntimeException("验证码不能为空..");
        }
        // 2)判断用户名或密码不能为空
        if(Strings.isNullOrEmpty(authVo.getUsername()) || Strings.isNullOrEmpty(authVo.getPassword())){
            throw new RuntimeException("用户名或密码不能为空..");
        }
        // 3)判断验证码和Session保存的验证码是否一致
        String vcode = (String) request.getSession().getAttribute("vcode");
        if(!authVo.getCode().equals(vcode)){
            throw new RuntimeException("验证码错误..");
        }

        //2.去数据库校验
        User user = userService.getUserByUsernameAndPassword(authVo.getUsername(),authVo.getPassword());
        //将登陆时间进行保存 可以使用rabbitmq，因为这类数据不是重要数据

        if(Objects.nonNull(user)){
            //将用户的关键信息保存到token中，后面可以根据token返回这些信息
            Map<String,Object> map = new HashMap<>();
            map.put("id",user.getId());
            map.put("username",user.getUsername());
            map.put("avatar",user.getAvatar());
            //还需进行将token放入到redis中，后续通过redis进行判断token是否有效

            String token = JwtUtils.createJwt(map);
            redisTemplate.opsForValue().set(RedisConstant.LOGIN_TOKEN_KEY + user.getId(),token,JwtUtils.EXPIRE, TimeUnit.SECONDS);

            return token;
        }else{
            throw new RuntimeException("用户名或密码错误...");
        }
    }

    @Override
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        String token1 = request.getHeader("token");
        if(StringUtils.hasLength(token1)){
            //获取token的过期时间
            Date expiration = JwtUtils.parseJwt(token1).getExpiration();
            if(expiration.after(new Date())){
                //TODO 这里可以对token进行进一步的验证
                //1.如果是不想多个token都生效，则与redis进行校验，如果不相同则进行替换，旧的token失效
//                String token = (String) redisTemplate.opsForValue().get("token");

                //2.如果发来的请求是有token，但是redis没有token，进行拒绝操作

                //3.可以从redis获取用户信息

                return JwtUtils.getPayLoadALSOExcludeExpAndIat(token1);
            }
        }

        return null;
    }

    /**
     * 退出登录
     * @param request HttpServletRequest
     */
    @Override
    public void logout(HttpServletRequest request) {
        Map<String, Object> userInfo = JwtUtils.getPayLoadALSOExcludeExpAndIat(request.getHeader("token"));
        Long id = Long.valueOf(userInfo.get("id").toString());

        redisTemplate.delete(RedisConstant.LOGIN_TOKEN_KEY + id);
    }

    /**
     * 注册用户
     * @param userRegisterVo 待注册的信息
     */
    @Override
    public String registerUser(UserRegisterVo userRegisterVo) {
        //利用validation校验器帮我们做基础校验

        //复杂校验
        //1.用户名是否存在
        if(userMapper.createBeforeCheck(null, userRegisterVo.getUsername()) > 0){
            throw new RuntimeException("该用户名已被注册，请换一个");
        }
        //2.邮箱是否存在
        if(userMapper.createBeforeCheck(userRegisterVo.getEmail(),null) > 0){
            throw new RuntimeException("该邮箱已被注册");
        }
        //3.验证码是否正确
        Object code = redisTemplate.opsForValue().get(RedisConstant.REGISTER_EMAIL_KEY + userRegisterVo.getEmail());
        if(Objects.isNull(code)){
            throw new RuntimeException("注册异常，请重新注册");
        }

        if(userRegisterVo.getCode().equals(code.toString())){
            //将Vo转为 实体表
            User user = new User();
            BeanUtils.copyProperties(userRegisterVo,user);
            int newUser = userMapper.createNewUser(user);
            //数据插入时，一定要保证数据的一致性，需要在查一下新用户
            if(newUser > 0){
                User user1 = userMapper.selectById(user.getId());

                //将用户的关键信息保存到token中，后面可以根据token返回这些信息
                Map<String,Object> map = new HashMap<>();
                map.put("id",user.getId());
                map.put("username",user.getUsername());
                map.put("avatar",user.getAvatar());
                //还需进行将token放入到redis中，后续通过redis进行判断token是否有效

                String token = JwtUtils.createJwt(map);
                redisTemplate.opsForValue().set(RedisConstant.LOGIN_TOKEN_KEY + user.getId(),token,JwtUtils.EXPIRE, TimeUnit.SECONDS);

                return token;
            }else{
                throw new RuntimeException("创建用户失败");
            }
        }else{
            throw new RuntimeException("验证码有误..");
        }
    }
}
