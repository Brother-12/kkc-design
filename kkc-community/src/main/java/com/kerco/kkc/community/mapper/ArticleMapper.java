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
     * @param article 文章
     * @return 删除结果
     */
    int deleteArticleById(Article article);

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

    /**
     * 前台 获取文章详细信息
     * @param id 文章id
     * @return 文章具体数据
     */
    ArticleShowVo getArticleShowById(Long id);

    /**
     * 根据用户id 获取用户发布的文章列表
     * @param id 用户id
     * @return 用户发布的文章列表
     */
    List<Article> getArticleListById(Long id);

    /**
     * 根据用户id 分页获取用户发表的文章列表
     * @param id 用户id
     * @param page 当前页数
     * @return 用户发表的文章列表
     */
    List<ArticleShowVo> getUserArticleShowList(@Param("id") Long id, @Param("page") Integer page,@Param("key") String key,@Param("tagId") Integer tagId);

    /**
     * 增加浏览次数
     * @param id 文章id
     */
    void incrArticleCount(@Param("id") Long id);

    /**
     * 获取100个最新的文章列表
     * @return 文章列表
     */
    List<CurrencyShowVo> randomArticleShow();

    /**
     * 修改文章
     * @param article 文章信息
     * @return 修改结果
     */
    int renewArticle(Article article);

    /**
     * 刷新点赞数量
     * @param id 文章id
     * @param count 点赞数量
     */
    int fixedTimeUpdateThumbsUp(@Param("id") Long id, @Param("thumbsup") Integer count);
}
