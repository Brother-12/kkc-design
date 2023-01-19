package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.mapper.ArticleContentMapper;
import com.kerco.kkc.community.service.ArticleContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentService {

    /**
     * 根据文章id 获取文章内容
     * @param articleId 文章id
     * @return 文章内容
     */
    @Override
    public ArticleContent getArticleContentById(Long articleId) {
        return this.getById(articleId);
    }
}
