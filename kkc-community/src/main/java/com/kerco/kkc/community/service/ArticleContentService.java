package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.ArticleContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface ArticleContentService extends IService<ArticleContent> {

    /**
     * 根据文章id 获取文章内容
     * @param articleId 文章id
     * @return 文章内容
     */
    ArticleContent getArticleContentById(Long articleId);

    /**
     * 保存文章内容
     * @param id 文章id
     * @param content 文章内容
     */
    void saveArticleContent(Long id, String content);

    /**
     * 修改文章文本
     * @param id 文章id
     * @param content 文章内容
     * @return 修改结果
     */
    int renewArticleContent(Long id, String content);
}
