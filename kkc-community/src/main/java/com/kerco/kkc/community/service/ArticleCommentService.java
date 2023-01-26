package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.entity.vo.CommentVo;
import com.kerco.kkc.community.entity.vo.PostCommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    /**
     * 根据文章id 获取文章的评论列表
     * @param id 文章id
     * @return 文章的评论列表
     */
    List<CommentVo> getArticleComment(Long id);

    /**
     * 发表评论
     * @param postCommentVo 发表的评论信息
     */
    void postComment(PostCommentVo postCommentVo);
}
