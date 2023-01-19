package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.utils.PageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页 获取文章列表
     * @param currentPage 当前页
     * @param key 搜索关键字
     * @return 分页文章列表数据
     */
    PageUtils getArticleList(Integer currentPage, String key,Integer official,Integer top,Integer essence,Integer examination,Integer status);

    /**
     * 获取文章详细信息
     * @return 获取文章详细信息
     */
    Article getArticleById(Long id);

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
