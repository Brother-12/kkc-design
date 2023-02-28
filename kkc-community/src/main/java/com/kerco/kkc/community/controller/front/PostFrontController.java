package com.kerco.kkc.community.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.ArticleComment;
import com.kerco.kkc.community.entity.vo.PostCommentVo;
import com.kerco.kkc.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/post")
@RestController
public class PostFrontController {

    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private QuestionCommentService questionCommentService;

    @Autowired
    private ArticleStarService articleStarService;

    @Autowired
    private QuestionStarService questionStarService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private QuestionService questionService;
    /**
     * 文章发表评论
     * @param postCommentVo 发表的评论信息
     * @return 发表结果
     */
    @PostMapping("/article/post")
    public CommonResult postArticleComment(@Valid @RequestBody PostCommentVo postCommentVo){
        articleCommentService.postComment(postCommentVo);

        return CommonResult.success("评论成功");
    }

    /**
     * 问答发表评论
     * @param postCommentVo 发表的评论信息
     * @return 发表结果
     */
    @PostMapping("/question/post")
    public CommonResult postQuestionComment(@Valid @RequestBody PostCommentVo postCommentVo){
        questionCommentService.postComment(postCommentVo);

        return CommonResult.success("评论成功");
    }

    /**
     * 文章点赞接口
     * @param id 文章id
     * @return 点赞结果
     */
    @PostMapping("/article/{id}/up")
    public CommonResult articleThumbsUp(@PathVariable Long id){
        articleStarService.articleThumbsUp(id);

        return CommonResult.success();
    }

    /**
     * 获取用户点赞文章状态
     * @param id
     * @return
     */
    @PostMapping("/article/{id}/status")
    public CommonResult articleThumbsUpStatus(@PathVariable Long id){
        boolean flag = articleStarService.getUserThumbsUpStatus(id);

        return CommonResult.success(flag);
    }

    /**
     * 问答点赞接口
     * @param id 文章id
     * @return 点赞结果
     */
    @PostMapping("/question/{id}/up")
    public CommonResult questionThumbsUp(@PathVariable Long id){
        questionStarService.questionThumbsUp(id);

        return CommonResult.success();
    }

    /**
     * 获取用户点赞文章状态
     * @param id
     * @return
     */
    @PostMapping("/question/{id}/status")
    public CommonResult questionThumbsUpStatus(@PathVariable Long id){
        boolean flag = questionStarService.getUserThumbsUpStatus(id);

        return CommonResult.success(flag);
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 删除结果
     */
    @PostMapping("/article/{id}/delete")
    public CommonResult deleteArticle(@PathVariable Long id){
        int result = articleService.deleteArticle(id);

        return CommonResult.success(result);
    }

    /**
     * 删除问答
     * @param id 问答id
     * @return 删除结果
     */
    @PostMapping("/question/{id}/delete")
    public CommonResult deleteQuestion(@PathVariable Long id){
        int result = questionService.deleteQuestion(id);

        return CommonResult.success(result);
    }
}
