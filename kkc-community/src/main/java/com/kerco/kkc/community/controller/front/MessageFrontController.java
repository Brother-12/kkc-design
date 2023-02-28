package com.kerco.kkc.community.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.vo.MessageVo;
import com.kerco.kkc.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/message")
@RestController
public class MessageFrontController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取个人关联的消息(点赞、评论)
     * @param currentPage 当前页数
     * @return 消息列表
     */
    @PostMapping("/show")
    public CommonResult getMessage(@RequestParam(value = "page",required = false) Integer currentPage){

        List<MessageVo> messageList = messageService.getMessage(currentPage);

        return CommonResult.success(messageList);
    }
}
