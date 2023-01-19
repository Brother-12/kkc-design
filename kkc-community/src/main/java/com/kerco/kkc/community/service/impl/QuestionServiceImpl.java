package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.vo.QuestionVo;
import com.kerco.kkc.community.entity.vo.TagTreeVo;
import com.kerco.kkc.community.mapper.QuestionMapper;
import com.kerco.kkc.community.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.service.TagService;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private TagService tagService;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public PageUtils getQuestionList(Integer currentPage, String key, Integer examination,Integer status) {
        Page<Question> page = null;
        //这里使用条件构造器来构建sql语句，配合我们使用的分页插件，生成分页数据
        QueryWrapper<Question> wrapper = new QueryWrapper<Question>();

        //1.判断是否有对文章标题进行关键字搜索
        if(StringUtils.hasLength(key)){
            wrapper.like("title", key);
        }

        //2.判断是否有对审核进行筛选
        if(!Objects.isNull(examination)){
            wrapper.eq("examination",examination);
        }

        //3.判断是否有对状态进行筛选
        if(!Objects.isNull(status)){
            wrapper.eq("status",status);
        }

        //如果当前页为空，则从第一页开始查找
        if(currentPage == null){
            page = this.page(new Page<Question>(1, 7), wrapper);
        }else{
            page = this.page(new Page<Question>(currentPage, 7), wrapper);
        }

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, String> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, TagTreeVo::getLabel));

        //将问答的关联的标签id 转为标签名
        List<Question> records = page.getRecords();
        List<QuestionVo> result = records.stream().map(v -> {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(v, questionVo);

            String[] split = v.getTagIds().split(",");
            List<String> tagNames = new ArrayList<>();
            for (String s : split) {
                tagNames.add(tagMap.get(Integer.parseInt(s)));
            }
            questionVo.setTagNames(tagNames);
            return questionVo;
        }).collect(Collectors.toList());

        return new PageUtils(result,(int) page.getTotal(), (int) page.getSize(),(int) page.getCurrent());
    }

    /**
     * 获取问答详细信息
     * @return 获取问答详细信息
     */
    @Override
    public Question getQuestionById(Long id) {
        return this.getById(id);
    }

    /**
     * 更新问答的特殊信息(状态、审核)
     * @param question 待更新的文章信息
     * @return 更新结果
     */
    @Override
    public int updateQuestionSpecialById(Question question) {
        return questionMapper.updateQuestionSpecialById(question);
    }

    /**
     * 根据id删除问答
     * @param id 问答id
     * @return 删除结果
     */
    @Override
    public int deleteQuestionById(Long id) {
        return questionMapper.deleteQuestionById(id);
    }
}
