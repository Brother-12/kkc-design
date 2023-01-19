package com.kerco.kkc.community.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@RefreshScope
@Api(value = "AdviceController",tags = {"建议类"})
@RestController
@RequestMapping("/community/advice")
public class AdviceController {
    @Value("${custom.community}")
    private String value;

    @GetMapping("/getAdvice")
    public String getAdvice(){
        return value;
    }
}
