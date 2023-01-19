package com.kerco.kkc.community.controller;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.QuestionContent;
import com.kerco.kkc.community.service.QuestionContentService;
import com.kerco.kkc.community.service.QuestionService;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/community/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionContentService questionContentService;

    /**
     * 分页 获取问答列表
     * @param currentPage 当前页
     * @param key 搜索关键字
     * @param examination 审核筛选
     * @return 分页问答列表数据
     */
    @GetMapping("/list")
    public CommonResult getQuestionList(@RequestParam(value = "currentPage",required = false) Integer currentPage,
                                       @RequestParam(value = "key",required = false) String key,
                                       @RequestParam(value = "examination",required = false) Integer examination,
                                       @RequestParam(value = "status",required = false) Integer status){
        PageUtils questionList = questionService.getQuestionList(currentPage,key,examination,status);

        CommonResult<PageUtils> result = CommonResult.success(questionList);
        return result;
    }

    /**
     * 根据问答id 获取问答内容
     * @param questionId 问答id
     * @return 问答内容
     */
    @GetMapping("/getContent")
    public CommonResult getQuestionContentById(@RequestParam(value = "questionId",required = false) Long questionId){
        QuestionContent content = questionContentService.getQuestionContentById(questionId);

        return CommonResult.success(content.getMdContent());
    }

    /**
     * 获取问答详细信息
     * @return 获取问答详细信息
     */
    @GetMapping("/getQuestion")
    public CommonResult getQuestionById(@RequestParam("id") Long id){
        if(id == null){
            // 这里需要一个全局错误代码来代替10000
            return CommonResult.error(10000,"问答id为空");
        }

        Question question = questionService.getQuestionById(id);

        CommonResult<Question> result = CommonResult.success(question);
        return result;
    }

    /**
     * 更新问答的特殊信息(状态、审核)
     * @param question 待更新的文章信息
     * @return 更新结果
     */
    @PostMapping("/update/special")
    public CommonResult updateQuestionById(@RequestBody Question question){
        int i = questionService.updateQuestionSpecialById(question);

        return CommonResult.success("更新成功");
    }

    /**
     * 根据id删除问答
     * @param id 问答id
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public CommonResult deleteQuestionById(@PathVariable("id") Long id){
        int result = questionService.deleteQuestionById(id);

        return CommonResult.success(result);
    }
}
