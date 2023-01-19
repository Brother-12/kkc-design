package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.QuestionContent;
import com.kerco.kkc.community.mapper.QuestionContentMapper;
import com.kerco.kkc.community.service.QuestionContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class QuestionContentServiceImpl extends ServiceImpl<QuestionContentMapper, QuestionContent> implements QuestionContentService {

    /**
     * 根据问答id获取内容
     * @param questionId 问答id
     * @return
     */
    @Override
    public QuestionContent getQuestionContentById(Long questionId) {
        return this.getById(questionId);
    }
}
