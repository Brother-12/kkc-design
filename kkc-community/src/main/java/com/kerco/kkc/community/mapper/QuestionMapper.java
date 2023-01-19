package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Question;
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
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 更新问答的特殊信息(状态、审核)
     * @param question 待更新的文章信息
     * @return 更新结果
     */
    int updateQuestionSpecialById(Question question);

    /**
     * 根据id删除问答
     * @param id 问答id
     * @return 删除结果
     */
    int deleteQuestionById(Long id);
}
