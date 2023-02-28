package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.QuestionStar;
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
public interface QuestionStarService extends IService<QuestionStar> {

    /**
     * 问答点赞接口
     * @param id 文章id
     * @return 点赞结果
     */
    void questionThumbsUp(Long id);

    /**
     * 根据问答id 获取用户点赞id，包括取消点赞的id
     * @param id 问答id
     * @return 用户id列表
     */
    List<String> getUserThumbsUpIdByQuestionId(Long id);

    /**
     * 获取指定问答点赞的总数
     * @param id 问答id
     * @return 点赞数量
     */
    Integer getUserThumbsUpCount(Long id);

    /**
     * 获取用户点赞状态
     * @param id 问答id
     * @return 状态
     */
    boolean getUserThumbsUpStatus(Long id);
}
