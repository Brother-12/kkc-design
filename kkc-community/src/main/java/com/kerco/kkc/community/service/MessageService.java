package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.entity.vo.MessageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface MessageService extends IService<Message> {

    /**
     * 接收文章消息处理
     * @param message 消息
     */
    void acceptArticleCommentMessage(Message message);

    /**
     * 接收问答消息处理
     * @param message 消息
     */
    void acceptQuestionCommentMessage(Message message);

    /**
     * 接收文章点赞消息
     * @param message 消息
     */
    void acceptArticleThumbsUpMessage(Message message);

    /**
     * 获取消息列表
     * @param currentPage 当前页数
     * @return 消息列表
     */
    List<MessageVo> getMessage(Integer currentPage);

    /**
     * 接收问答点赞消息
     * @param message 消息
     */
    void acceptQuestionThumbsUpMessage(Message message);


}
