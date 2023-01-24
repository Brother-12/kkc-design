package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.QuestionContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface QuestionContentMapper extends BaseMapper<QuestionContent> {

    /**
     * 保存问答内容
     * @param content1
     * @return
     */
    int saveQuestionContent(QuestionContent content1);
}
