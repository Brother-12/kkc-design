package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.vo.*;
import com.kerco.kkc.community.feign.MemberFeign;
import com.kerco.kkc.community.mapper.QuestionMapper;
import com.kerco.kkc.community.service.CategoryService;
import com.kerco.kkc.community.service.QuestionContentService;
import com.kerco.kkc.community.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.service.TagService;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
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

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Autowired
    private QuestionContentService contentService;

    @Autowired
    private CategoryService categoryService;

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

    /**
     * 写问答
     * @param questionWriteVo 写问答要保存的信息
     * @param request 写入结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Transactional
    @Override
    public void writeQuestion(QuestionWriteVo questionWriteVo, HttpServletRequest request) throws ExecutionException, InterruptedException {
        String token = request.getHeader("token");
        Map<String, Object> userData = JwtUtils.getPayLoadALSOExcludeExpAndIat(token);
        //1.验证token里的id与作者id是否一致
        if(Long.parseLong(userData.get("id").toString()) == questionWriteVo.getAuthorId()){
            CommonResult<UserTo> result = memberFeign.getUserByIdToWrite(questionWriteVo.getAuthorId());
            //3.对引用的标签进行+1操作
            CompletableFuture<Integer> countFuture = CompletableFuture.supplyAsync(() -> {
                return tagService.incrRefCount(questionWriteVo.getCategoryId(), questionWriteVo.getTagIds());
            }, poolExecutor);
            UserTo user1 = result.getData();
            if(countFuture.get() > 0){
                Question question = new Question();
                question.setAuthorId(user1.getId());
                question.setAuthorAvatar(user1.getAvatar());
                question.setAuthorUsername(user1.getUsername());
                question.setTitle(questionWriteVo.getTitle());
                //需要将List<Integer> tag标签集合 转为 字符串，用','来进行分隔
                String tagList = String.join(",", questionWriteVo.getTagIds().stream().map(v -> String.valueOf(v)).collect(Collectors.toList()));
                question.setTagIds(tagList);
                //4.插入 问答信息
                questionMapper.createQuestion(question);

                //5.保存问答内容
                contentService.saveQuestion(question.getId(),questionWriteVo.getContent());
            }
        }
    }

    /**
     * 获取首页的 最新问答和精选问答
     * @return
     */
    @Override
    public Map<String, Object> getIndexQuestion() {
        Map<String,Object> map = new HashMap<>();
        //1.获取最新问答
        List<CurrencyShowVo> recentList = questionMapper.getRecentQuestion();

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, TagTreeVo> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, (v)-> v));

        //根据tag_id 获取具体的tag关键信息
        recentList.forEach(v -> {
            String[] split = v.getTagIds().split(",");
            List<TagTreeVo> list = new ArrayList<>();
            for (String s : split) {
                TagTreeVo tagTreeVo = tagMap.get(Integer.parseInt(s));
                if(Objects.nonNull(tagTreeVo)){
                    list.add(tagTreeVo);
                }
            }
            v.setTagList(list);
        });

        //2.获取精选问答
        List<SpecialVo> specialList = questionMapper.getSpecialQuestion();
        map.put("recent",recentList);
        map.put("special",specialList);
        return map;
    }

    /**
     * 分页获取 问答列表页 与筛选条件
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param condition 10000（最新）、10001（最热）
     * @param page 当前页数
     * @return 分页后的问答列表
     */
    @Override
    public List<QuestionShowVo> getQuestionShowList(Integer categoryId, Integer tagId, Integer condition, Integer page) {
        //对categoryId和tagId和condition进行初始化工作
        categoryId = Objects.isNull(categoryId) ? 1 : categoryId;
        tagId = Objects.isNull(tagId) ? 1 : tagId;
        condition = Objects.isNull(condition) ? 10000 : condition;

        //对于category为1且tagId不为1，则表示无效操作，直接返回空
        if(categoryId == 1 && tagId != 1){
            return null;
        }
        //如果当前页为空或者输入的页数小于1，则直接从第一页获取
        if(page == null || page < 1){
            page = 1;
        }

        //用来接收标签id，作用是根据标签id去进行筛选
        List<String> list = new ArrayList<>();
        //对于categoryId不等于1和tagId不等于1的判断
        if(categoryId != 1 && tagId != 1){
            boolean flag = false;
            //获取分类树
            List<CategoryTreeVo> categoryTree = categoryService.getCategoryTree();
            //获取tag的id信息，主要是用来判断 tagId是否在categoryId下边
            for (CategoryTreeVo categoryTreeVo : categoryTree) {
                //如果搜索的分类id与categoryTreeVo.getId()相同，则对该分类下的所有标签id进行标签判断，如果都没有则表示是乱输入的，直接返回空
                if(categoryTreeVo.getId().equals(categoryId)){
                    for (TagTreeVo child : categoryTreeVo.getChildren()) {
                        if(tagId.equals(child.getId())){
                            flag = true;
                            break;
                        }
                    }
                    break;
                }
            }
            //如果搜索的tagId不属于categoryId，则什么都不搜索直接返回
            if(!flag){
                return null;
            }
            //将满足条件的标签id加入集合中
            list.add(tagId.toString());
        }
        //对请求的参数进行初始化操作(如果没有值的话)
        else if(categoryId != 1 && tagId == 1){
            list = tagService.getTagListByCategoryId(categoryId);
        }

        //把条件添加到map集合中，方便管理
        Map<String,Object> map = new HashMap<>();
        map.put("page",(page - 1) * 10);
        map.put("tagId",list);
        map.put("categoryId",categoryId);
        map.put("condition",condition);

        List<QuestionShowVo> questionList = questionMapper.getQuestionShowList(map);

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, TagTreeVo> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, (v)-> v));

        //根据tag_id 获取具体的tag关键信息
        questionList.forEach(v -> {
            String[] split = v.getTagIds().split(",");
            List<TagTreeVo> list1 = new ArrayList<>();
            for (String s : split) {
                TagTreeVo tagTreeVo = tagMap.get(Integer.parseInt(s));
                if(Objects.nonNull(tagTreeVo)){
                    list1.add(tagTreeVo);
                }
            }
            v.setTagList(list1);
        });

        return questionList;
    }
}
