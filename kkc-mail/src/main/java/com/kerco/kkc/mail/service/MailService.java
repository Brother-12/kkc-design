package com.kerco.kkc.mail.service;


public interface MailService {
    /**
     * 使用html模板发送
     * @param email 邮箱
     */
    void useHtmlTemplateSend(String email);
}
