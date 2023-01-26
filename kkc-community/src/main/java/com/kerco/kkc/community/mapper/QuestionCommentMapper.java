package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.QuestionComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.community.entity.vo.CommentVo;
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
public interface QuestionCommentMapper extends BaseMapper<QuestionComment> {

    /**
     * 根据问答id 获取问答的评论列表
     * @param id 问答id
     * @return 问答的评论列表
     */
    List<QuestionComment> getQuestionComment(Long id);

    /**
     * 发表评论
     * @param questionComment 评论数据
     * @return 插入结果
     */
    int postComment(QuestionComment questionComment);
}
