package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.common.constant.RedisConstant;
import com.kerco.kkc.community.entity.ArticleStar;
import com.kerco.kkc.community.entity.Message;
import com.kerco.kkc.community.interceptor.LoginInterceptor;
import com.kerco.kkc.community.mapper.ArticleStarMapper;
import com.kerco.kkc.community.service.ArticleStarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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
 * @since 2023-02-23
 */
@Service
public class ArticleStarServiceImpl extends ServiceImpl<ArticleStarMapper, ArticleStar> implements ArticleStarService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ArticleStarMapper articleStarMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void articleThumbsUp(Long id) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());
        //获取set操作类
        SetOperations<String, String> setOptions = redisTemplate.opsForSet();

        //如果没有 用户点赞id列表，则创建出来
        if(setOptions.size(RedisConstant.THUMBSUP_ARTICLE_USERID_LIST+id) == null){
            List<String> userIds = getUserThumbsUpIdByArticleId(id);
            userIds.forEach(tempUserId -> {
                setOptions.add(RedisConstant.THUMBSUP_ARTICLE_USERID_LIST+id,tempUserId);
            });
        }

        //TODO 后续替换成lua脚本来保证原子操作
        if(setOptions.isMember(RedisConstant.THUMBSUP_ARTICLE_USERID_LIST + id, userId.toString())){
            int status = articleStarMapper.getUserThumbsUpStatus(id, userId);
            int expectStatus = status == 1 ? 0 : 1;
            //这一步进行取反操作
            articleStarMapper.updateThumbsUpStatus(id,userId,expectStatus);

            if(status == 0){
                //如果status = 0，说明当前操作时是取消点赞操作，操作数减1
                redisTemplate.opsForValue().decrement(RedisConstant.ARTICLE_THUMBSUP_COUNT+id);
            }else{
                //如果status = 1，说明当前操作时是点赞操作，操作数加1
                redisTemplate.opsForValue().increment(RedisConstant.ARTICLE_THUMBSUP_COUNT+id);
            }
        }else{
            setOptions.add(RedisConstant.THUMBSUP_ARTICLE_USERID_LIST+id,userId.toString());
            //如果不是，直接新建一条记录
            ArticleStar articleStar = new ArticleStar();
            articleStar.setArticleId(id);
            articleStar.setUserId(userId);
            articleStarMapper.articleThumbsUp(articleStar);

            //点赞总数+1
            redisTemplate.opsForValue().increment(RedisConstant.ARTICLE_THUMBSUP_COUNT+id);

            //实现点赞消息存储
            //TODO 后续替换成rabbitmq来处理消息，要不然会出现循环引用。当前就设置循环引用为true
            Message message = new Message();
            //设置发起人信息
            message.setClientId(userId);
            message.setClientUsername(userInfo.get("username").toString());
            //考虑到没有头像的情况
            if(userInfo.get("avatar") != null){
                message.setClientAvatar(userInfo.get("avatar").toString());
            }
            //设置消息类型
            message.setOptType(0);  //操作类型：点赞
            message.setOptOperation(0); //操作类型：文章
            message.setOptId(id);   //操作对象：文章id

            messageService.acceptArticleThumbsUpMessage(message);
        }
    }

    /**
     * 根据文章id 获取用户点赞id，包括取消点赞的id
     * @param id 文章id
     * @return 用户id列表
     */
    @Override
    public List<String> getUserThumbsUpIdByArticleId(Long id) {
        return articleStarMapper.getUserThumbsUpIdByArticleId(id);
    }

    /**
     * 根据文章id 获取点赞数
     * @param id 文章id
     * @return 点赞数
     */
    @Override
    public Integer getUserThumbsCountByArticleId(Long id) {
        return articleStarMapper.getUserThumbsCountByArticleId(id);
    }

    /**
     * 根据用户id和文章id获取 点赞状态
     * @param articleId 文章id
     * @return 点赞状态
     */
    @Override
    public boolean getUserThumbsUpStatus(Long articleId) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        if(Objects.isNull(userInfo)){
            //考虑到没有登陆的情况，直接返回false
            return false;
        }

        Long userId = Long.parseLong(userInfo.get("id").toString());
        Integer status = articleStarMapper.getUserThumbsUpStatus(articleId, userId);

        return status == null ? false : (status == 1 ? false : true);
    }
}
