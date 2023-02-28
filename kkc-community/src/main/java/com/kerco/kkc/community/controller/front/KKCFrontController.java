package com.kerco.kkc.community.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.ArticleComment;
import com.kerco.kkc.community.entity.vo.*;
import com.kerco.kkc.community.service.*;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 前台主要的 控制器，该控制器定义的方法基本都是公共方法，可以直接获取不用token
 * 后续在加入安全操作
 */
@RequestMapping("/kkc")
@RestController
public class KKCFrontController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private QuestionCommentService questionCommentService;

    @Autowired
    private SearchService searchService;

    /**
     * 获取分类节点
     * @return 分类节点列表
     */
    @GetMapping("/category/treeList")
    public CommonResult getCategoryTreeList(){
        List<CategoryTreeVo> categoryTree = categoryService.getCategoryTree();
        return CommonResult.success(categoryTree);
    }

    /**
     * 获取首页的最新文章和精选文章
     * @return 最新文章和精选文章集合
     */
    @GetMapping("/index/article/list")
    public CommonResult getIndexArticle(){
        Map<String, Object> indexArticle = articleService.getIndexArticle();

        return CommonResult.success(indexArticle);
    }

    /**
     * 获取首页的最新问答和精选问答
     * @return 最新问答和精选问答集合
     */
    @GetMapping("/index/question/list")
    public CommonResult getIndexQuestion(){
        Map<String, Object> indexQuestion = questionService.getIndexQuestion();

        return CommonResult.success(indexQuestion);
    }

    /**
     * 分页获取 文章列表页 与筛选条件
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param condition 10000（最新）、10001（最热）
     * @param page 当前页数
     * @return 分页后的文章列表
     */
    @GetMapping("/nav/article")
    public CommonResult getArticleShowList(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                                           @RequestParam(value = "tagId",required = false) Integer tagId,
                                           @RequestParam(value = "condition",required = false) Integer condition,
                                           @RequestParam(value = "currentPage",required = false) Integer page){

        List<ArticleShowVo> result = articleService.getArticleShowList(categoryId,tagId,condition,page);

        return CommonResult.success(result);
    }

    /**
     * 分页获取 问答列表页 与筛选条件
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param condition 10000（最新）、10001（最热）、10002（最多回答）
     * @param page 当前页数
     * @return 分页后的问答列表
     */
    @GetMapping("/nav/question")
    public CommonResult getQuestionShowList(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                                           @RequestParam(value = "tagId",required = false) Integer tagId,
                                           @RequestParam(value = "condition",required = false) Integer condition,
                                           @RequestParam(value = "currentPage",required = false) Integer page){

        List<QuestionShowVo> result = questionService.getQuestionShowList(categoryId,tagId,condition,page);

        return CommonResult.success(result);
    }

    /**
     * 获取文章详细页
     * @param id 文章id
     * @return 文章详细页
     */
    @GetMapping("/article/detail")
    public CommonResult getArticleById(@RequestParam("id") Long id){
        ArticleShowVo articleShowVo = articleService.getArticleShowById(id);

        return CommonResult.success(articleShowVo);
    }

    /**
     * 获取用户的成就
     * @param id 用户id
     * @return 用户的成就信息
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/user/achievement")
    public CommonResult getUserAchievement(@RequestParam("id") Long id) throws ExecutionException, InterruptedException {
        UserAchievementVo userAchievementVo = achievementService.getUserAchievement(id);

        return CommonResult.success(userAchievementVo);
    }

    /**
     * 根据文章id 获取文章的评论列表
     * @param id 文章id
     * @return 文章的评论列表
     */
    @GetMapping("/article/comment")
    public CommonResult getArticleComment(@RequestParam("id") Long id){
        List<CommentVo> list = articleCommentService.getArticleComment(id);

        return CommonResult.success(list);
    }

    /**
     * 获取问答的详细信息
     * @param id 问答id
     * @return 问答的详细信息
     */
    @GetMapping("/question/detail")
    public CommonResult getQuestionById(@RequestParam("id") Long id){
        QuestionShowVo questionShowVo = questionService.getQuestionShowById(id);

        return CommonResult.success(questionShowVo);
    }

    /**
     * 根据问答id 获取问答的评论列表
     * @param id 问答id
     * @return 问答的评论列表
     */
    @GetMapping("/question/comment")
    public CommonResult getQuestionComment(@RequestParam("id") Long id){
        List<CommentVo> list = questionCommentService.getQuestionComment(id);
        return CommonResult.success(list);
    }

    /**
     * 根据用户id 分页获取用户发表的文章列表
     * @param id 用户id
     * @param page 当前页数
     * @return 用户发表的文章列表
     */
    @GetMapping("/article/user/publish")
    public CommonResult getUserArticleShowList(@RequestParam("id") Long id,
                                               @RequestParam(value = "currentPage",required = false) Integer page){
        List<ArticleShowVo> result =  articleService.getUserArticleShowList(id,page);

        return CommonResult.success(result);
    }

    /**
     * 根据用户id 分页获取用户发表的问答列表
     * @param id 用户id
     * @param page 当前页数
     * @return 用户发表的问答列表
     */
    @GetMapping("/question/user/publish")
    public CommonResult getUserQuestionShowList(@RequestParam("id") Long id,
                                               @RequestParam(value = "currentPage",required = false) Integer page){
        List<QuestionShowVo> result =  questionService.getUserQuestionShowList(id,page);

        return CommonResult.success(result);
    }

    /**
     * 搜索接口 - 根据关键字搜索
     * @param page 页数
     * @param key 关键字
     * @return 搜索结果分页
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/search")
    public CommonResult searchKey(@RequestParam(value = "currentPage",required = false) Integer page,
                                  @RequestParam("key") String key) throws ExecutionException, InterruptedException {
        Map<String, Object> stringObjectMap = searchService.searchKey(key, page);

        return CommonResult.success(stringObjectMap);
    }

    /**
     * 搜索接口 - 根据标签搜索
     * @param page 页数
     * @param id 标签id
     * @return 搜索结果分页
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/tag/search")
    public CommonResult searchTag(@RequestParam(value = "currentPage",required = false) Integer page,
                                  @RequestParam("id") Integer id) throws ExecutionException, InterruptedException {
        Map<String, Object> stringObjectMap = searchService.searchTag(id, page);

        return CommonResult.success(stringObjectMap);
    }

    /**
     * 获取随机文章列表
     * @return 文章列表
     */
    @GetMapping("/random/article")
    public CommonResult randomArticleShow(){

        List<CurrencyShowVo> currencyShowVoList = articleService.randomArticleShow();

        return CommonResult.success(currencyShowVoList);
    }

    /**
     * 获取随机问答列表
     * @return 问答列表
     */
    @GetMapping("/random/question")
    public CommonResult randomQuestionShow(){

        List<CurrencyShowVo> currencyShowVoList = questionService.randomQuestionShow();

        return CommonResult.success(currencyShowVoList);
    }
}
