package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.vo.ArticleVo;
import com.kerco.kkc.community.entity.vo.TagTreeVo;
import com.kerco.kkc.community.mapper.ArticleMapper;
import com.kerco.kkc.community.service.ArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

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
}
