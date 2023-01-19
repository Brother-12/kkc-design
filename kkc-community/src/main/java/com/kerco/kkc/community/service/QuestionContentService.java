package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.QuestionContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface QuestionContentService extends IService<QuestionContent> {

    /**
     * 根据问答id 获取问答内容
     * @param questionId 问答id
     * @return 问答内容
     */
    QuestionContent getQuestionContentById(Long questionId);
}
