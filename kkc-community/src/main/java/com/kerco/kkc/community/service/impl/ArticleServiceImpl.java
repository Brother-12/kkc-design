package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.common.constant.RedisConstant;
import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Question;
import com.kerco.kkc.community.entity.to.User;
import com.kerco.kkc.community.entity.vo.*;
import com.kerco.kkc.community.feign.MemberFeign;
import com.kerco.kkc.community.interceptor.LoginInterceptor;
import com.kerco.kkc.community.mapper.ArticleMapper;
import com.kerco.kkc.community.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.utils.PageUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    @Autowired
    private ArticleStarService articleStarService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

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
        Article article = new Article();
        article.setId(id);

        return articleMapper.deleteArticleById(article);
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
        Map<String, Object> userData = LoginInterceptor._toThreadLocal.get();
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

            //TODO 缺少对分类的id进行验证
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

        int count = -1;

        if(!redisTemplate.hasKey(RedisConstant.ARTICLE_THUMBSUP_COUNT+id)){
            //开启异步处理
            CompletableFuture.runAsync(()->{
                //如果没在redis中存储过，则将id存储到redis中
                //TODO 待优化 点赞用户id的存储结构
                List<String> userIds = articleStarService.getUserThumbsUpIdByArticleId(id);
                userIds.forEach(userId -> {
                    redisTemplate.opsForSet().add(RedisConstant.THUMBSUP_ARTICLE_USERID_LIST+id,userId);
                });

                //保存点赞数量
                Integer currentCount = articleStarService.getUserThumbsCountByArticleId(id);
                redisTemplate.opsForValue().set(RedisConstant.ARTICLE_THUMBSUP_COUNT+id, currentCount+"");
            },threadPoolExecutor);
        }else{
            //从缓存中获取点赞数
            String s = redisTemplate.opsForValue().get(RedisConstant.ARTICLE_THUMBSUP_COUNT + id);
            count = Integer.parseInt(s == null ? "0" : s);
        }

        ArticleShowVo articleShowById = articleMapper.getArticleShowById(id);
        if(count != -1){
            articleShowById.setThumbsup(count);
        }

        return articleShowById;
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

    /**
     * 随机获取缓存中5个保存的文章 缓存中存储100个最新文章
     * @return 随机文章列表
     */
    @Override
    public List<CurrencyShowVo> randomArticleShow() {
        return articleMapper.randomArticleShow();
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 删除结果
     */
    @Override
    public int deleteArticle(Long id) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());
        //删除前查询一下 文章是否存在
        Article article = this.getArticleById(id);
        //如果文章不为空，同时文章作者id 与当前登陆的id相同，则删除
        if(Objects.nonNull(article) && userId.equals(article.getAuthorId())){
            return this.deleteArticleById(id);
        }
        return 0;
    }

    /**
     * 获取编辑文章
     * @param map 接收要修改的文章id
     * @return 文章内容
     */
    @Override
    public CurrencyShowVo getEditArticle(Map<String, String> map) {
        //先从请求体中获取文章id
        Long articleId = Long.parseLong(map.get("id"));
        //获取文章展示信息
        ArticleShowVo article = articleMapper.getArticleShowById(articleId);
        //从token中获取用户信息，取出用户id
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());
        //如果用户id与文章的作者id相同，才允许返回内容
        if(userId.equals(article.getAuthorId())){
            return article;
        }

        return null;
    }

    /**
     * 修改文章
     * @param articleEditVo 要修改的文章信息
     * @return 修改结果
     */
    @Transactional
    @Override
    public int renewArticle(ArticleEditVo articleEditVo) {
        Map<String, Object> userInfo = LoginInterceptor._toThreadLocal.get();
        Long userId = Long.parseLong(userInfo.get("id").toString());

        //判断登陆的用户id 与文章的作者id是否一致
        if(userId.equals(articleEditVo.getAuthorId())){
            //文章信息
            Article article = new Article();
            article.setId(articleEditVo.getId());
            article.setTitle(articleEditVo.getTitle());
            article.setAuthorId(articleEditVo.getAuthorId());
            //重新将审核状态进行重置，文章编辑之后需要重新审核
            article.setExamination(0);
            String tagList = String.join(",", articleEditVo.getTagIds().stream().map(v -> String.valueOf(v)).collect(Collectors.toList()));
            article.setTagIds(tagList);

            int result = articleMapper.renewArticle(article);
            int result2 = articleContentService.renewArticleContent(articleEditVo.getId(), articleEditVo.getContent());
            if(result == 1 && result2 == 1){
                return 1;
            }else{
                throw new IllegalArgumentException("更新文章异常");
            }
        }
        return 0;
    }

    /**
     * 定时更新 文章表里面的点赞数
     */
    @Override
    public void fixedTimeUpdateThumbsUp() {
        //匹配所有在redis中存储的key，已 article:thumbsup:count: 为前缀
        Set<String> keys = redisTemplate.keys(RedisConstant.ARTICLE_THUMBSUP_COUNT + "*");
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        for (String key : keys) {
            Integer count = Integer.parseInt(operations.get(key));
            String id = key.substring(key.lastIndexOf(":")+1);
            articleMapper.fixedTimeUpdateThumbsUp(Long.parseLong(id),count);
        }
    }
}
