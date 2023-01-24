package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.community.entity.vo.ArticleShowVo;
import com.kerco.kkc.community.entity.vo.ArticleWriteVo;
import com.kerco.kkc.community.entity.vo.CurrencyShowVo;
import com.kerco.kkc.community.entity.vo.SpecialVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 创建文章
     * @param article 文章内容
     */
    void createArticle(Article article);

    /**
     * 获取最新的三篇 文章
     */
    List<ArticleShowVo> getRecentArticle();

    /**
     * 获取精选文章
     */
    List<SpecialVo> getSpecialArticle();

    /**
     * 分页获取 文章列表页 的筛选条件
     * @return 分页后的文章列表
     */
    List<ArticleShowVo> getArticleShowList(Map<String,Object> map);
}
