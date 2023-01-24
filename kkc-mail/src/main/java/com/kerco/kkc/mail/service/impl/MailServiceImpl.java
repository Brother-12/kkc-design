package com.kerco.kkc.mail.service.impl;

import com.kerco.kkc.common.constant.RedisConstant;
import com.kerco.kkc.mail.constant.MailConstant;
import com.kerco.kkc.mail.service.MailService;
import com.kerco.kkc.mail.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MailUtils mailUtils;

    public void useHtmlTemplateSend(String email) {
        //操作前先校验邮箱是否格式正确
        if(!isValidEmail(email)){
            throw new RuntimeException("邮箱格式不正确，请重新输入");
        }

        //预防多次点击：如果已经发送过验证码，而且多次点击，则什么都不执行
        if(redisTemplate.opsForValue().get(RedisConstant.REGISTER_EMAIL_KEY + email) != null){
            return ;
        }

        //获取验证码
        String randomCode = mailUtils.getRandomCode();
        //获取html模板
        String content = mailUtils.buildContent(email, randomCode);

        mailUtils.sendHtmlMail(email, MailConstant.title,content);
        redisTemplate.opsForValue().set(RedisConstant.REGISTER_EMAIL_KEY + email,randomCode,RedisConstant.REGISTER_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }
}
