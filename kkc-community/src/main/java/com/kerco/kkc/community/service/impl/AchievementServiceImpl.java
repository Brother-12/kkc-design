package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.vo.UserAchievementVo;
import com.kerco.kkc.community.service.AchievementService;
import com.kerco.kkc.community.service.ArticleService;
import com.kerco.kkc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 获取用户成就信息
     *
     *  开启异步出现的多线程问题：数据不一致
     *      在两个线程执行的时候，如果同时对一个数据进行相加，则可以回出现部分数据不一致问题。
     *      原因：一个线程执行时，cpu时间片用完，对内存环境进行保存。然后另一个线程执行，发现数据还是0没有操作，然后执行完。
     *          接着等待的线程有了cpu资源后，恢复环境，当时手中的值还是0。
     *      解决：要么使用原子的引用类型，要么
     * @param id 用户id
     * @return 用户成就信息
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public UserAchievementVo getUserAchievement(Long id) throws ExecutionException, InterruptedException {
        //利用AtomicInteger原子类解决 并发问题
        AtomicInteger thumbsUpCount = new AtomicInteger();
        AtomicInteger originalCount = new AtomicInteger();
        AtomicInteger viewsCount = new AtomicInteger();
        UserAchievementVo userAchievementVo = new UserAchievementVo();
        //1.获取用户发表的文章列表和问答列表，并且统计发表数量及获取的点赞数
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            //局部变量，用来统计点赞数与浏览数之和
            int thumbs = 0;
            int views = 0;
            List<Article> articleList = articleService.getArticleListById(id);
            for (Article article : articleList) {
                thumbs+=article.getThumbsup();
                views+=article.getViews();
            }
            //利用原子类进行相加操作
            while(!thumbsUpCount.compareAndSet(thumbsUpCount.get(),thumbsUpCount.get() + thumbs)){}
            while(!originalCount.compareAndSet(originalCount.get(),originalCount.get() + articleList.size())){}
            while(!viewsCount.compareAndSet(viewsCount.get(),viewsCount.get() + views)){}
        }, executor);

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            //局部变量，用来统计点赞数与浏览数之和
            int thumbs = 0;
            int views = 0;
            List<Question> questionList = questionService.getQuestionListById(id);
            for (Question question : questionList) {
                thumbs += question.getThumbsup();
                views += question.getViews();
            }
            //利用原子类进行相加操作
            while(!thumbsUpCount.compareAndSet(thumbsUpCount.get(),thumbsUpCount.get() + thumbs)){}
            while(!originalCount.compareAndSet(originalCount.get(),originalCount.get() + questionList.size())){}
            while(!viewsCount.compareAndSet(viewsCount.get(),viewsCount.get() + views)){}
        }, executor);

        //等待所有异步任务的完成
        CompletableFuture.allOf(future,future1).get();

        //赋值
        userAchievementVo.setOriginalCount(originalCount.get());
        userAchievementVo.setThumbsUpCount(thumbsUpCount.get());
        userAchievementVo.setViewsCount(viewsCount.get());

        //TODO 收藏数与关注数还没有实现
        return userAchievementVo;
    }
}
