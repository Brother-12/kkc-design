package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.ArticleStar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-02-23
 */
@Mapper
public interface ArticleStarMapper extends BaseMapper<ArticleStar> {

    /**
     * 根据文章id 获取用户点赞id，包括取消点赞的id
     * @param id 文章id
     * @return 用户id列表
     */
    List<String> getUserThumbsUpIdByArticleId(Long id);

    /**
     * 用户点赞
     * @param articleStar 点赞信息
     */
    int articleThumbsUp(ArticleStar articleStar);

    /**
     * 修改用户点赞状态
     * @param id 文章id
     * @param userId 用户id
     */
    int updateThumbsUpStatus(@Param("articleId") Long id, @Param("userId") Long userId,@Param("status") Integer status);

    /**
     * 获取点赞状态
     * @param id 文章id
     * @param userId 用户id
     * @return
     */
    int getUserThumbsUpStatus(@Param("articleId") Long id, @Param("userId") Long userId);

    /**
     * 根据文章id 获取点赞数
     * @param id 文章id
     * @return 点赞数
     */
    Integer getUserThumbsCountByArticleId(Long id);
}
