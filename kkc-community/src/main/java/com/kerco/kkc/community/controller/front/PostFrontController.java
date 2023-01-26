package com.kerco.kkc.community.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.ArticleComment;
import com.kerco.kkc.community.entity.vo.PostCommentVo;
import com.kerco.kkc.community.service.ArticleCommentService;
import com.kerco.kkc.community.service.QuestionCommentService;
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
}
