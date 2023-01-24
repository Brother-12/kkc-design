package com.kerco.kkc.mail;

import com.kerco.kkc.mail.constant.MailConstant;
import com.kerco.kkc.mail.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KkcMailApplicationTests {

    @Autowired
    private MailUtils mailUtils;

    @Test
    void contextLoads() {
        String email = "2920387292@qq.com";

        //获取验证码
        String randomCode = mailUtils.getRandomCode();

        //获取html模板
        String content = mailUtils.buildContent(email, randomCode);

        mailUtils.sendHtmlMail(email, MailConstant.title,content);
    }
}
