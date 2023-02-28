package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.QuestionStar;
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
public interface QuestionStarMapper extends BaseMapper<QuestionStar> {

    /**
     * 根据文章id 获取用户点赞id，包括取消点赞的id
     * @param id 文章id
     * @return 用户id列表
     */
    List<String> getUserThumbsUpIdByQuestionId(Long id);

    /**
     * 返回用户点赞的状态
     * @param id 问答id
     * @param userId 用户id
     * @return 状态
     */
    Integer getUserThumbsUpStatus(@Param("questionId") Long id, @Param("userId") Long userId);

    /**
     * 修改用户点赞状态
     * @param id 问答id
     * @param userId 用户id
     */
    int updateThumbsUpStatus(@Param("questionId") Long id, @Param("userId") Long userId,@Param("status") Integer status);

    /**
     * 用户点赞
     * @param questionStar 点赞信息
     */
    int questionThumbsUp(QuestionStar questionStar);

    /**
     * 获取指定问答点赞的总数
     * @param id 问答id
     * @return 点赞数量
     */
    Integer getUserThumbsUpCount(Long id);
}
