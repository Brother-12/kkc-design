package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.to.User;
import com.kerco.kkc.community.entity.vo.*;
import com.kerco.kkc.community.feign.MemberFeign;
import com.kerco.kkc.community.mapper.ArticleMapper;
import com.kerco.kkc.community.service.ArticleContentService;
import com.kerco.kkc.community.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.service.CategoryService;
import com.kerco.kkc.community.service.TagService;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleContentService articleContentService;

    @Autowired
    private TagService tagService;

    @Autowired
    private MemberFeign memberFeign;

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页 获取文章列表
     * @param currentPage 当前页
     * @param key 搜索关键字
     * @return 分页文章列表数据
     */
    @Override
    public PageUtils getArticleList(Integer currentPage, String key,Integer official,Integer top,Integer essence,Integer examination,Integer status) {
        Page<Article> page = null;
        //这里使用条件构造器来构建sql语句，配合我们使用的分页插件，生成分页数据
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();

        //1.判断是否有对文章标题进行关键字搜索
        if(StringUtils.hasLength(key)){
            wrapper.like("title", key);
        }

        //2.判断是否有对官方进行筛选
        if(!Objects.isNull(official)){
            wrapper.eq("official",official);
        }

        //3.判断是否有对置顶进行筛选
        if(!Objects.isNull(top)){
            wrapper.eq("top",top);
        }

        //4.判断是否有对精华进行筛选
        if(!Objects.isNull(essence)){
            wrapper.eq("essence",essence);
        }

        //5.判断是否有对精华进行筛选
        if(!Objects.isNull(examination)){
            wrapper.eq("examination",examination);
        }

        //6.判断是否有对状态进行筛选
        if(!Objects.isNull(status)){
            wrapper.eq("status",status);
        }

        //如果当前页为空，则从第一页开始查找
        if(currentPage == null){
            page = this.page(new Page<Article>(1, 7), wrapper);
        }else{
            page = this.page(new Page<Article>(currentPage, 7), wrapper);
        }

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, String> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, TagTreeVo::getLabel));

        //将文章的关联的标签id 转为标签名
        List<Article> records = page.getRecords();
        List<ArticleVo> result = records.stream().map(v -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(v, articleVo);
            System.out.println("123："+v.getCreateTime());
            String[] split = v.getTagIds().split(",");
            List<String> tagNames = new ArrayList<>();
            for (String s : split) {
                tagNames.add(tagMap.get(Integer.parseInt(s)));
            }
            articleVo.setTagNames(tagNames);
            return articleVo;
        }).collect(Collectors.toList());

        return new PageUtils(result,(int) page.getTotal(), (int) page.getSize(),(int) page.getCurrent());
    }

    /**
     * 获取文章详细信息
     * @return 获取文章详细信息
     */
    @Override
    public Article getArticleById(Long id) {
        return this.getById(id);
    }

    /**
     * 更新文章的特殊信息(官方、置顶、精华)
     * @param article 待更新的文章信息
     * @return 更新结果
     */
    @Override
    public int updateArticleSpecialById(Article article) {
        return articleMapper.updateArticleSpecialById(article);
    }

    /**
     * 根据id删除文章
     * @param id 文章id
     * @return 删除结果
     */
    @Override
    public int deleteArticleById(Long id) {
        return articleMapper.deleteArticleById(id);
    }

    /**
     * 写文章
     * @param articleWriteVo 写文章要保存的信息
     * @param request HttpServletRequest
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Transactional
    @Override
    public void writeArticle(ArticleWriteVo articleWriteVo, HttpServletRequest request) throws ExecutionException, InterruptedException {
        String token = request.getHeader("token");
        Map<String, Object> userData = JwtUtils.getPayLoadALSOExcludeExpAndIat(token);
        //1.验证token里的id与作者id是否一致
        if(Long.parseLong(userData.get("id").toString()) == articleWriteVo.getAuthorId()){
            //2.获取用户最新的信息
//            CompletableFuture<UserTo> userFuture = CompletableFuture.supplyAsync(() -> {
//                CommonResult<UserTo> result = memberFeign.getUserByIdToWrite(articleWriteVo.getAuthorId());
//                System.out.println(result);
//                return result.getData();
//            }, poolExecutor);
            CommonResult<UserTo> result = memberFeign.getUserByIdToWrite(articleWriteVo.getAuthorId());
            //3.对引用的标签进行+1操作
            CompletableFuture<Integer> countFuture = CompletableFuture.supplyAsync(() -> {
                return tagService.incrRefCount(articleWriteVo.getCategoryId(), articleWriteVo.getTagIds());
            }, poolExecutor);

            UserTo user1 = result.getData();
            if(countFuture.get() > 0){
                Article article = new Article();
                article.setTitle(articleWriteVo.getTitle());
                article.setAuthorId(articleWriteVo.getAuthorId());
                article.setAuthorUsername(user1.getUsername());
                article.setAuthorAvatar(user1.getAvatar());
                String tagList = String.join(",", articleWriteVo.getTagIds().stream().map(v -> String.valueOf(v)).collect(Collectors.toList()));
                article.setTagIds(tagList);
                //4.保存文章
                articleMapper.createArticle(article);

                //5.保存文章内容
                articleContentService.saveArticleContent(article.getId(),articleWriteVo.getContent());
            }
        }else{
            throw new RuntimeException("文章发布出现异常，请稍后再试");
        }
    }

    /**
     * 获取首页的 最新文章和精选文章
     */
    @Override
    public Map<String,Object> getIndexArticle() {
        Map<String,Object> map = new HashMap<>();
        //1.获取最新文章
        List<ArticleShowVo> recentList = articleMapper.getRecentArticle();

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

        //1.获取精选文章
        List<SpecialVo> specialArticle = articleMapper.getSpecialArticle();
        map.put("recent",recentList);
        map.put("special",specialArticle);
        return map;
    }

    /**
     * 分页获取 文章列表页 的筛选条件
     *
     * 对于分类标签导航栏的点击，处理过程
     *  1.categoryId和tagId如果都是1，则什么都不操作，就根据条件及页数返回数据
     *  2.categoryId不是1，tagId是1，则返回该所有标签关联该分类的数据，在根据这些标签去搜索
     *  3.categoryId不是1，tagId不是1，则判断tagId是否关联于categoryId，如果没有关联则直接返回空
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param condition 10000（最新）、10001（最热）
     * @param page 当前页数
     * @return 分页后的文章列表
     */
    @Override
    public List<ArticleShowVo> getArticleShowList(Integer categoryId, Integer tagId, Integer condition, Integer page) {
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

        List<ArticleShowVo> articleList = articleMapper.getArticleShowList(map);

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, TagTreeVo> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, (v)-> v));

        //根据tag_id 获取具体的tag关键信息
        articleList.forEach(v -> {
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
        return articleList;
    }

    /**
     * 前台 获取文章详细信息 同时增加浏览次数
     * @param id 文章id
     * @return 文章具体数据
     */
    @Override
    public ArticleShowVo getArticleShowById(Long id) {
        //增加浏览文章次数
        articleMapper.incrArticleCount(id);
        return articleMapper.getArticleShowById(id);
    }

    /**
     * 根据用户id 获取用户发布的文章列表
     * @param id 用户id
     * @return 用户发布的文章列表
     */
    @Override
    public List<Article> getArticleListById(Long id) {
        return articleMapper.getArticleListById(id);
    }

    /**
     * 根据用户id 分页获取用户发表的文章列表
     * @param id 用户id
     * @param page 当前页数
     * @return 用户发表的文章列表
     */
    @Override
    public List<ArticleShowVo> getUserArticleShowList(Long id, Integer page) {
        if(page == null || page < 1){
            page = 1;
        }

        //获取用户发表的文章列表
        List<ArticleShowVo> articleList = articleMapper.getUserArticleShowList(id,(page - 1) * 10,null,null);

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, TagTreeVo> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, (v)-> v));

        //根据tag_id 获取具体的tag关键信息
        articleList.forEach(v -> {
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

        return articleList;
    }

    /**
     * 根据关键字 获取文章列表
     * @param key 关键字
     * @param page 当前页
     * @return 文章列表
     */
    @Override
    public List<ArticleShowVo> getArticleShowByKey(String key,Integer page) {
        if(page == null || page < 1){
            page = 1;
        }

        //获取用户发表的文章列表
        List<ArticleShowVo> articleList = articleMapper.getUserArticleShowList(null,(page - 1) * 10,'%'+key+'%',null);

        if(articleList.size() == 0){
            return articleList;
        }

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, TagTreeVo> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, (v)-> v));

        //根据tag_id 获取具体的tag关键信息
        articleList.forEach(v -> {
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

        return articleList;
    }

    /**
     * 根据标签id 获取文章列表
     * @param id 标签id
     * @param page 当前页
     * @return 搜索结果
     */
    @Override
    public List<ArticleShowVo> getArticleShowByTagId(Integer id, Integer page) {
        if(page == null || page < 1){
            page = 1;
        }

        //获取用户发表的文章列表
        List<ArticleShowVo> articleList = articleMapper.getUserArticleShowList(null,(page - 1) * 10,null,id);

        if(articleList.size() == 0){
            return articleList;
        }

        //获取tag标签关键信息
        List<TagTreeVo> keyTagList = tagService.getKeyTagList();
        //转为map集合，加快查询速度
        Map<Integer, TagTreeVo> tagMap = keyTagList.stream().collect(Collectors.toMap(TagTreeVo::getId, (v)-> v));

        //根据tag_id 获取具体的tag关键信息
        articleList.forEach(v -> {
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

        return articleList;
    }
}
