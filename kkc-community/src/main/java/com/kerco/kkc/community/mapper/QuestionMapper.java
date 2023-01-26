package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.community.entity.vo.CurrencyShowVo;
import com.kerco.kkc.community.entity.vo.QuestionShowVo;
import com.kerco.kkc.community.entity.vo.SpecialVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

    /**
     * 创建问答
     * @param question 待插入的问答数据
     * @return 创建结果
     */
    int createQuestion(Question question);

    /**
     * 获取最新的问答列表数据
     * @return  最新的问答列表数据
     */
    List<CurrencyShowVo> getRecentQuestion();

    /**
     * 获取精选的问答列表数据
     * @return  精选的问答列表数据
     */
    List<SpecialVo> getSpecialQuestion();

    List<QuestionShowVo> getQuestionShowList(Map<String, Object> map);

    /**
     * 根据用户id 获取用户发布的问答列表
     * @param id 用户id
     * @return 用户发布的问答列表
     */
    List<Question> getQuestionListById(Long id);

    /**
     * 根据问答id 获取问答具体信息
     * @param id 问答id
     * @return QuestionShowVo
     */
    QuestionShowVo getQuestionShowById(Long id);
}
