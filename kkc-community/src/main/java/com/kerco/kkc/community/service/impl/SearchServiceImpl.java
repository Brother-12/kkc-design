package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.vo.ArticleShowVo;
import com.kerco.kkc.community.entity.vo.QuestionShowVo;
import com.kerco.kkc.community.service.ArticleService;
import com.kerco.kkc.community.service.QuestionService;
import com.kerco.kkc.community.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 关键字搜索
     * @param key 关键字
     */
    @Override
    public Map<String,Object> searchKey(String key,Integer page) throws ExecutionException, InterruptedException {
        CompletableFuture<List<ArticleShowVo>> articleFuture = CompletableFuture.supplyAsync(() -> articleService.getArticleShowByKey(key, page),executor);

        CompletableFuture<List<QuestionShowVo>> questionFuture = CompletableFuture.supplyAsync(() -> questionService.getQuestionShowByKey(key, page),executor);

        //等待所有异步任务处理完
        CompletableFuture.allOf(articleFuture,questionFuture).get();

        List<ArticleShowVo> articleList = articleFuture.get();
        List<QuestionShowVo> questionList = questionFuture.get();

        Map<String,Object> map = new HashMap<>();
        map.put("article",articleList);
        map.put("question",questionList);

        return map;
    }

    /**
     * 标签搜索
     * @param id 标签id
     * @param page 当前页
     * @return 搜索结果
     */
    @Override
    public Map<String, Object> searchTag(Integer id, Integer page) throws ExecutionException, InterruptedException {
        CompletableFuture<List<ArticleShowVo>> articleFuture = CompletableFuture.supplyAsync(() -> articleService.getArticleShowByTagId(id, page),executor);

        CompletableFuture<List<QuestionShowVo>> questionFuture = CompletableFuture.supplyAsync(() -> questionService.getQuestionShowByTagId(id, page),executor);

        //等待所有异步任务处理完
        CompletableFuture.allOf(articleFuture,questionFuture).get();

        List<ArticleShowVo> articleList = articleFuture.get();
        List<QuestionShowVo> questionList = questionFuture.get();

        Map<String,Object> map = new HashMap<>();
        map.put("article",articleList);
        map.put("question",questionList);

        return map;
    }
}
