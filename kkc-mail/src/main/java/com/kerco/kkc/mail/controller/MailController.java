package com.kerco.kkc.mail.controller;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendCode")
    public CommonResult sendCode(@RequestBody Map<String,String> map){
        String email = map.get("email");
        mailService.useHtmlTemplateSend(email);

        return CommonResult.success("发送成功");
    }
}
