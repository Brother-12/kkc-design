package com.kerco.kkc.common.constant;

public class RedisConstant {
    //登陆成功的key
    public final static String LOGIN_TOKEN_KEY = "login:token:";

    //注册用户的key
    public final static String REGISTER_EMAIL_KEY = "register:email:";

    //注册的验证码过期时间 5分钟
    public final static Integer REGISTER_EXPIRE_TIME = 60 * 5;

    //文章 用户点赞id列表，包括所有取消点赞id
    public final static String THUMBSUP_ARTICLE_USERID_LIST = "article:thumbsup:list:";

    //文章点赞数量
    public final static String ARTICLE_THUMBSUP_COUNT = "article:thumbsup:count:" ;

    //文章随机列表，存放
    public final static String RANDOM_ARTICLE_LIST = "article:random:list:100";

    //问答 用户点赞id列表，包括所有取消点赞id
    public final static String THUMBSUP_QUESTION_USERID_LIST = "question:thumbsup:list:";

    //问答点赞数量
    public final static String QUESTION_THUMBSUP_COUNT = "question:thumbsup:count:" ;

    //问答随机列表，存放
    public final static String RANDOM_QUESTION_LIST = "question:random:list:100";
}
