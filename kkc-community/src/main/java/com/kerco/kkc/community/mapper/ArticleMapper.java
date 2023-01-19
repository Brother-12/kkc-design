package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Article;
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
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 更新文章的特殊信息(官方、置顶、精华)
     * @param article 待更新的文章信息
     * @return 更新结果
     */
    int updateArticleSpecialById(Article article);

    /**
     * 根据id删除文章
     * @param id 文章id
     * @return 删除结果
     */
    int deleteArticleById(Long id);
}
