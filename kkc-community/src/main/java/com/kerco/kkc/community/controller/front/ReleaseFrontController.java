package com.kerco.kkc.community.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.vo.ArticleEditVo;
import com.kerco.kkc.community.entity.vo.ArticleWriteVo;
import com.kerco.kkc.community.entity.vo.CurrencyShowVo;
import com.kerco.kkc.community.entity.vo.QuestionWriteVo;
import com.kerco.kkc.community.service.ArticleService;
import com.kerco.kkc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 写 文章/问答 控制器
 */
@RequestMapping("/write")
@RestController
public class ReleaseFrontController {

    @Resource
    ArticleService articleService;

    @Autowired
    private QuestionService questionService;

    /**
     * 写文章
     * @param articleWriteVo 写文章要保存的信息
     * @return 写入结果
     */
    @PostMapping("/article/write")
    public CommonResult writeArticle(@Valid @RequestBody ArticleWriteVo articleWriteVo, HttpServletRequest request) throws ExecutionException, InterruptedException {
        articleService.writeArticle(articleWriteVo,request);

        return CommonResult.success("发布成功");
    }

    /**
     * 写问答
     * @param questionWriteVo 写问答要保存的信息
     * @return 写入结果
     */
    @PostMapping("/question/write")
    public CommonResult writeQuestion(@Valid @RequestBody QuestionWriteVo questionWriteVo, HttpServletRequest request) throws ExecutionException, InterruptedException {
        questionService.writeQuestion(questionWriteVo,request);

        return CommonResult.success("发布成功");
    }

    /**
     * 编辑文章
     * @param map 接收要修改的文章id
     * @return 文章内容
     */
    @PostMapping("/article/edit")
    public CommonResult getEditArticle(@RequestBody Map<String,String> map){
        CurrencyShowVo currencyShowVo = articleService.getEditArticle(map);

        return CommonResult.success(currencyShowVo);
    }

    /**
     * 修改文章
     * @param articleEditVo 要修改的文章信息
     * @return 修改结果
     */
    @PostMapping("/article/editSave")
    public CommonResult renewArticle(@Valid @RequestBody ArticleEditVo articleEditVo){
        int result = articleService.renewArticle(articleEditVo);

        return CommonResult.success(result);
    }
}
