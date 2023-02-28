package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Message;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.vo.MessageVo;
import com.kerco.kkc.community.feign.MemberFeign;
import com.kerco.kkc.community.interceptor.LoginInterceptor;
import com.kerco.kkc.community.mapper.MessageMapper;
import com.kerco.kkc.community.service.ArticleService;
import com.kerco.kkc.community.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    private QuestionService questionService;

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 接收消息处理
     * @param message 消息
     */
    @Override
    public void acceptArticleCommentMessage(Message message) {
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

    @Override
    public void acceptQuestionCommentMessage(Message message) {
        Question question = questionService.getQuestionById(message.getOptId());

        //设置消息的标题
        message.setOptTitle(question.getTitle());

        //如果为空，则是一级评论，则userId是文章作者id
        if(Objects.isNull(message.getUserId())){
            message.setUserId(question.getAuthorId());
        }

        CommonResult<UserTo> result = memberFeign.getUserByIdToWrite(message.getClientId());
        //用户数据
        UserTo user = result.getData();
        message.setClientUsername(user.getUsername());
        message.setClientAvatar(user.getAvatar());

        //保存消息
        messageMapper.saveMessage(message);
    }

    /**
     * 接收文章点赞消息
     * @param message 消息
     */
    @Override
    public void acceptArticleThumbsUpMessage(Message message){
        //根据文章id 获取文章信息
        Article article = articleService.getArticleById(message.getOptId());

        //设置消息的标题
        message.setOptTitle(article.getTitle());

        //设置接收人id
        message.setUserId(article.getAuthorId());

        messageMapper.saveMessage(message);
    }

    /**
     * 获取消息列表
     * @param currentPage 当前页数
     * @return 消息列表
     */
    @Override
    public List<MessageVo> getMessage(Integer currentPage) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());

        if(currentPage == null){
            currentPage = 1;
        }

        return messageMapper.getMessage(userId,(currentPage - 1) * 10);
    }

    @Override
    public void acceptQuestionThumbsUpMessage(Message message) {
        Long questionId = message.getOptId();

        //设置消息的标题
        Question question = questionService.getQuestionById(questionId);
        message.setOptTitle(question.getTitle());

        //设置接收人id
        message.setUserId(question.getAuthorId());

        //保存消息
        messageMapper.saveMessage(message);
    }
}
