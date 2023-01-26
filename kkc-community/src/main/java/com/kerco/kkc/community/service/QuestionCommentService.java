package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.QuestionComment;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface QuestionCommentService extends IService<QuestionComment> {

    /**
     * 根据问答id 获取问答的评论列表
     * @param id 问答id
     * @return 问答的评论列表
     */
    List<CommentVo> getQuestionComment(Long id);

    /**
     * 发表评论
     * @param postCommentVo 发表的评论信息
     */
    void postComment(PostCommentVo postCommentVo);
}
