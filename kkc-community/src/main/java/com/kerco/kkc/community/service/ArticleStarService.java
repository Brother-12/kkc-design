package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.ArticleStar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-02-23
 */
public interface ArticleStarService extends IService<ArticleStar> {

    /**
     * 根据文章id 获取所有点赞过的用户id，包括取消点赞的
     * @param id 文章id
     * @return 用户id 列表
     */
    List<String> getUserThumbsUpIdByArticleId(Long id);

    /**
     * 根据文章id 获取点赞数
     * @param id 文章id
     * @return 点赞数
     */
    Integer getUserThumbsCountByArticleId(Long id);

    void articleThumbsUp(Long id);

    /**
     * 根据用户id和文章id获取 点赞状态
     * @param articleId 文章id
     * @return 点赞状态
     */
    boolean getUserThumbsUpStatus(Long articleId);
}
