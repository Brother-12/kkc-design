package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.utils.PageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface QuestionService extends IService<Question> {

    /**
     * 分页 获取问答列表
     * @param currentPage 当前页
     * @param key 搜索关键字
     * @param examination 审核筛选
     * @return 分页问答列表数据
     */
    PageUtils getQuestionList(Integer currentPage, String key, Integer examination,Integer status);

    /**
     * 获取问答详细信息
     * @return 获取问答详细信息
     */
    Question getQuestionById(Long id);

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
