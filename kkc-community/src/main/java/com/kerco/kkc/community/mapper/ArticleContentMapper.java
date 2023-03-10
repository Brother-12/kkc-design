package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.ArticleContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {

    int saveArticleContent(ArticleContent content1);

    /**
     * 修改文章内容
     * @param content1 文章文本内容
     */
    int renewArticleContent(ArticleContent content1);
}
