package com.kerco.kkc.common.constant;

public class RedisConstant {
    //登陆成功的key
    public final static String LOGIN_TOKEN_KEY = "login:token:";

    //注册用户的key
    public final static String REGISTER_EMAIL_KEY = "register:email:";

    //注册的验证码过期时间 5分钟
    public final static Integer REGISTER_EXPIRE_TIME = 60 * 5;
}
