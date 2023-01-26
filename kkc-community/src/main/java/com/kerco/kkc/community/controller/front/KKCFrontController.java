package com.kerco.kkc.community.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.ArticleComment;
import com.kerco.kkc.community.entity.vo.*;
import com.kerco.kkc.community.service.*;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param condition 10000（最新）、10001（最热）
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

    @GetMapping("/article/detail")
    public CommonResult getArticleById(@RequestParam("id") Long id){
        ArticleShowVo articleShowVo = articleService.getArticleShowById(id);

        return CommonResult.success(articleShowVo);
    }

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
}
