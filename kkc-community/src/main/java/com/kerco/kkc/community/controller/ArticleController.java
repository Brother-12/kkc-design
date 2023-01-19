package com.kerco.kkc.community.controller;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.service.ArticleCommentService;
import com.kerco.kkc.community.service.ArticleContentService;
import com.kerco.kkc.community.service.ArticleService;
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
@RequestMapping("/community/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleContentService articleContentService;

    /**
     * 分页 获取文章列表
     * @param currentPage 当前页
     * @param key 搜索关键字
     * @return 分页文章列表数据
     */
    @GetMapping("/list")
    public CommonResult getArticleList(@RequestParam(value = "currentPage",required = false) Integer currentPage,
                                    @RequestParam(value = "key",required = false) String key,
                                    @RequestParam(value = "official",required = false) Integer official,
                                       @RequestParam(value = "top",required = false) Integer top,
                                       @RequestParam(value = "essence",required = false) Integer essence,
                                       @RequestParam(value = "examination",required = false) Integer examination,
                                       @RequestParam(value = "status",required = false) Integer status){
        PageUtils articleList = articleService.getArticleList(currentPage,key,official,top,essence,examination,status);

        CommonResult<PageUtils> result = CommonResult.success(articleList);
        return result;
    }

    /**
     * 根据文章id 获取文章内容
     * @param articleId 文章id
     * @return 文章内容
     */
    @GetMapping("/getContent")
    public CommonResult getArticleContentById(@RequestParam(value = "articleId",required = false) Long articleId){
        ArticleContent content = articleContentService.getArticleContentById(articleId);

        return CommonResult.success(content.getMdContent());
    }

    /**
     * 获取文章详细信息
     * @return 获取文章详细信息
     */
    @GetMapping("/getArticle")
    public CommonResult getArticleById(@RequestParam("id") Long id){
        if(id == null){
            // 这里需要一个全局错误代码来代替10000
            return CommonResult.error(10000,"用户id为空");
        }

        Article article = articleService.getArticleById(id);

        CommonResult<Article> result = CommonResult.success(article);
        return result;
    }

    /**
     * 更新文章的特殊信息(官方、置顶、精华)
     * @param article 待更新的文章信息
     * @return 更新结果
     */
    @PostMapping("/update/special")
    public CommonResult updateArticleById(@RequestBody Article article){
        int i = articleService.updateArticleSpecialById(article);

        return CommonResult.success("更新成功");
    }

    /**
     * 根据id删除文章
     * @param id 文章id
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public CommonResult deleteArticleById(@PathVariable("id") Long id){
        int result = articleService.deleteArticleById(id);

        return CommonResult.success(result);
    }
}
