package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.common.constant.RedisConstant;
import com.kerco.kkc.community.entity.ArticleStar;
import com.kerco.kkc.community.entity.Message;
import com.kerco.kkc.community.entity.QuestionStar;
import com.kerco.kkc.community.interceptor.LoginInterceptor;
import com.kerco.kkc.community.mapper.QuestionStarMapper;
import com.kerco.kkc.community.service.MessageService;
import com.kerco.kkc.community.service.QuestionStarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-02-23
 */
@Service
public class QuestionStarServiceImpl extends ServiceImpl<QuestionStarMapper, QuestionStar> implements QuestionStarService {

    @Autowired
    private QuestionStarMapper questionStarMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MessageService messageService;

    /**
     * 问答点赞接口
     * @param id 文章id
     * @return 点赞结果
     */
    @Override
    public void questionThumbsUp(Long id) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());
        //获取set操作类
        SetOperations<String, String> setOptions = redisTemplate.opsForSet();

        //如果没有 用户点赞id列表，则创建出来
        if(redisTemplate.hasKey(RedisConstant.THUMBSUP_QUESTION_USERID_LIST + id)){
            List<String> userIds = getUserThumbsUpIdByQuestionId(id);
            userIds.forEach(tempUserId -> {
                setOptions.add(RedisConstant.THUMBSUP_QUESTION_USERID_LIST+id,tempUserId);
            });
        }

        //TODO 后续替换成lua脚本来保证原子操作
        if(setOptions.isMember(RedisConstant.THUMBSUP_QUESTION_USERID_LIST + id, userId.toString())){
            int status = questionStarMapper.getUserThumbsUpStatus(id, userId);
            int expectStatus = status == 1 ? 0 : 1;
            //这一步进行取反操作
            questionStarMapper.updateThumbsUpStatus(id,userId,expectStatus);

            if(status == 0){
                //如果status = 0，说明当前操作时是取消点赞操作，操作数减1
                redisTemplate.opsForValue().decrement(RedisConstant.QUESTION_THUMBSUP_COUNT+id);
            }else{
                //如果status = 1，说明当前操作时是点赞操作，操作数加1
                redisTemplate.opsForValue().increment(RedisConstant.QUESTION_THUMBSUP_COUNT+id);
            }
        }else{
            setOptions.add(RedisConstant.THUMBSUP_QUESTION_USERID_LIST+id,userId.toString());
            //如果不是，直接新建一条记录
            QuestionStar questionStar = new QuestionStar();
            questionStar.setQuestionId(id);
            questionStar.setUserId(userId);
            questionStarMapper.questionThumbsUp(questionStar);

            //点赞总数+1
            redisTemplate.opsForValue().increment(RedisConstant.QUESTION_THUMBSUP_COUNT+id);

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
            message.setOptType(1);  //操作类型：问答
            message.setOptOperation(0); //操作类型：点赞
            message.setOptId(id);   //操作对象：问答id

            messageService.acceptQuestionThumbsUpMessage(message);
        }
    }

    /**
     * 根据文章id 获取用户点赞id，包括取消点赞的id
     * @param id 文章id
     * @return 用户id列表
     */
    @Override
    public List<String> getUserThumbsUpIdByQuestionId(Long id) {
        return questionStarMapper.getUserThumbsUpIdByQuestionId(id);
    }

    /**
     * 获取指定问答点赞的总数
     * @param id 问答id
     * @return 点赞数量
     */
    @Override
    public Integer getUserThumbsUpCount(Long id) {
        return questionStarMapper.getUserThumbsUpCount(id);
    }

    @Override
    public boolean getUserThumbsUpStatus(Long id) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());

        Integer status = questionStarMapper.getUserThumbsUpStatus(id, userId);

        return status == null ? false : (status == 1 ? false : true);
    }
}
