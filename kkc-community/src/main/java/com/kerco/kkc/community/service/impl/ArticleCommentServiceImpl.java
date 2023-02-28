package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.common.entity.UserKeyTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.ArticleComment;
import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.entity.Message;
import com.kerco.kkc.community.entity.vo.CommentVo;
import com.kerco.kkc.community.entity.vo.PostCommentVo;
import com.kerco.kkc.community.feign.MemberFeign;
import com.kerco.kkc.community.mapper.ArticleCommentMapper;
import com.kerco.kkc.community.service.ArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private MessageService messageService;

    /**
     * 根据文章id 获取文章的评论列表
     * @param id 文章id
     * @return 文章的评论列表
     */
    @Override
    public List<CommentVo> getArticleComment(Long id) {

        List<ArticleComment> articleComment = articleCommentMapper.getArticleComment(id);

        //如果该文章没有评论的话，直接返回null
        if(articleComment.size() == 0){
            return null;
        }

        //如果有评论，则获取所有评论者的id
        List<Long> commentId = articleComment.stream().map(v -> v.getCommentId()).collect(Collectors.toList());
        //评论者的id 集合 去远程调用，返回评论者的关键信息（id、用户名、头像）
        CommonResult<Map<Long, UserKeyTo>> userList = memberFeign.getUserListByIds(commentId);
        Map<Long, UserKeyTo> userMap = userList.getData();

        //TODO 后续需要对这里进行代码重写，思路不对，虽然能实现
        //当前思路
        //1.将所有一级分类先筛选成一个list集合，因为sql语句默认对parentId进行了升序操作，所以就能筛选出来
        List<CommentVo> list = new ArrayList<>();
        //转为map主要是方便，第二轮循环时，直接根据 一级评论的id，找到一级评论同时将自己添加到subComment中
        //形成一个树
        Map<Long, CommentVo> topCategory = articleComment.stream().filter(a -> a.getParentId() == 0)
                .map(a -> {
                    //将一级分类 转为 前台需要展示的CommentVo
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(a, commentVo);
                    commentVo.setUserInfo(userMap.get(a.getCommentId()));
                    //直接放入到list集合中，后面处理完能直接返回，不用在进行一次转换
                    list.add(commentVo);
                    return commentVo;
                }).collect(Collectors.toMap(CommentVo::getId, v -> v));

        //第二轮循环，将二级评论添加到一级评论的subComment中
        articleComment.stream().filter(a -> a.getParentId() != 0)
                .forEach(a -> {
                    //根据getParentId找到一级评论
                    CommentVo parentCommentVo = topCategory.get(a.getParentId());
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(a, commentVo);
                    commentVo.setUserInfo(userMap.get(a.getCommentId()));
                    commentVo.setReplyUsername(userMap.get(a.getReplyId()).getUsername());
                    //添加到一级评论的subComment中
                    if(parentCommentVo.getSubComment() == null){
                        parentCommentVo.setSubComment(new ArrayList<>());
                    }
                    parentCommentVo.getSubComment().add(commentVo);
                });

        return list;
    }

    /**
     * 发表评论
     * @param postCommentVo 发表的评论信息
     */
    @Override
    public void postComment(PostCommentVo postCommentVo) {
        //将PostCommentVo转为评论表对象ArticleComment
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(postCommentVo.getId());
        articleComment.setCommentContent(postCommentVo.getCommentContent());
        articleComment.setCommentId(postCommentVo.getCommentId());
        //如果parentId为null，说明是一级评论
        if(Objects.isNull(postCommentVo.getParentId())){
            articleComment.setParentId(0L);
        }else{
            articleComment.setParentId(postCommentVo.getParentId());
        }
        //如果replyId为null，说明是一级评论
        if(Objects.isNull(postCommentVo.getReplyId())){
            articleComment.setReplyId(0L);
        }else{
            articleComment.setReplyId(postCommentVo.getReplyId());
        }

        int i = articleCommentMapper.postComment(articleComment);
        if(i == 0){
            throw new RuntimeException("评论出现异常...");
        }else{
            //TODO 在这里实现 用户评论消息
            //思路
            //1.使用rabbitmq的消息机制 添加消息
            //2.开启异步任务 添加消息
            Message message = new Message();
            //运行到这边就说明评论已经加入到数据库了，所以直接判断即可
            if(Objects.isNull(postCommentVo.getParentId()) && Objects.isNull(postCommentVo.getReplyId())){
                message.setOptOperation(1);
                //如果是一级评论，则消息的接收是 文章作者id

            }else{
                message.setOptOperation(2);
                //如果是二级评论，则消息的接收是 一级评论的用户id
                message.setUserId(postCommentVo.getReplyId());
            }
            message.setOptType(0);
            message.setOptId(postCommentVo.getId());
            message.setClientId(postCommentVo.getCommentId());

            messageService.acceptArticleCommentMessage(message);
        }
    }
}
