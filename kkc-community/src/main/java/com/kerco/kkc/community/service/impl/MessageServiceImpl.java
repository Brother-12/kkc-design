package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Message;
import com.kerco.kkc.community.feign.MemberFeign;
import com.kerco.kkc.community.mapper.MessageMapper;
import com.kerco.kkc.community.service.ArticleService;
import com.kerco.kkc.community.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 接收消息处理
     * @param message 消息
     */
    @Override
    public void acceptCommentMessage(Message message) {
        //根据文章id 获取文章信息
        Article article = articleService.getArticleById(message.getOptId());

        //设置消息的标题
        message.setOptTitle(article.getTitle());

        //如果为空，则是一级评论，则userId是文章作者id
        if(Objects.isNull(message.getUserId())){
            message.setUserId(article.getAuthorId());
        }

        //还要去kkc-member获取用户的用户名及头像
        CommonResult<UserTo> userByIdToWrite = memberFeign.getUserByIdToWrite(message.getClientId());
        //用户数据
        UserTo user = userByIdToWrite.getData();
        message.setClientUsername(user.getUsername());
        message.setClientAvatar(user.getAvatar());

        //保存消息
        messageMapper.saveMessage(message);
    }
}
