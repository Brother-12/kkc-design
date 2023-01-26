package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    /**
     * 根据文章id 获取文章的评论列表
     * @param id 文章id
     * @return 文章的评论列表
     */
    List<ArticleComment> getArticleComment(Long id);

    /**
     * 发表评论
     * @param articleComment 待添加发表评论的信息
     */
    int postComment(ArticleComment articleComment);
}
