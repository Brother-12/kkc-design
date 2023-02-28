package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.entity.vo.CurrencyShowVo;
import com.kerco.kkc.community.entity.vo.QuestionShowVo;
import com.kerco.kkc.community.entity.vo.QuestionWriteVo;
import com.kerco.kkc.community.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    /**
     * 写问答
     * @param questionWriteVo 写问答要保存的信息
     * @param request 写入结果
     */
    void writeQuestion(QuestionWriteVo questionWriteVo, HttpServletRequest request) throws ExecutionException, InterruptedException;

    /**
     * 获取首页的问答列表
     * @return 问答数据
     */
    Map<String, Object> getIndexQuestion();

    /**
     * 分页获取 问答列表页 与筛选条件
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param condition 10000（最新）、10001（最热）
     * @param page 当前页数
     * @return 分页后的问答列表
     */
    List<QuestionShowVo> getQuestionShowList(Integer categoryId, Integer tagId, Integer condition, Integer page);

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

    /**
     * 根据用户id 分页获取用户发表的问答列表
     * @param id 用户id
     * @param page 当前页数
     * @return 用户发表的问答列表
     */
    List<QuestionShowVo> getUserQuestionShowList(Long id, Integer page);

    /**
     * 根据关键字获取问答列表
     * @param key 关键字
     * @param page 当前页
     * @return 问答列表
     */
    List<QuestionShowVo> getQuestionShowByKey(String key, Integer page);

    /**
     * 根据标签id 获取问答列表
     * @param id 标签id
     * @param page 当前页
     * @return 搜索结果
     */
    List<QuestionShowVo> getQuestionShowByTagId(Integer id, Integer page);

    /**
     * 获取随机问答列表
     * @return 问答列表
     */
    List<CurrencyShowVo> randomQuestionShow();

    /**
     * 删除问答
     * @param id 问答id
     * @return 删除结果
     */
    int deleteQuestion(Long id);
}
