package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.entity.vo.*;
import com.kerco.kkc.community.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页 获取文章列表
     * @param currentPage 当前页
     * @param key 搜索关键字
     * @return 分页文章列表数据
     */
    PageUtils getArticleList(Integer currentPage, String key,Integer official,Integer top,Integer essence,Integer examination,Integer status);

    /**
     * 获取文章详细信息
     * @return 获取文章详细信息
     */
    Article getArticleById(Long id);

    /**
     * 更新文章的特殊信息(官方、置顶、精华)
     * @param article 待更新的文章信息
     * @return 更新结果
     */
    int updateArticleSpecialById(Article article);

    /**
     * 根据id删除文章
     * @param id 文章id
     * @return 删除结果
     */
    int deleteArticleById(Long id);

    /**
     * 写文章
     * @param articleWriteVo 写文章要保存的信息
     * @return 写入结果
     */
    void writeArticle(ArticleWriteVo articleWriteVo, HttpServletRequest request) throws ExecutionException, InterruptedException;

    /**
     * 获取首页的 最新文章和精选文章
     */
    Map<String,Object> getIndexArticle();

    /**
     * 分页获取 文章列表页 的筛选条件
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param condition 10000（最新）、10001（最热）
     * @param page 当前页数
     * @return 分页后的文章列表
     */
    List<ArticleShowVo> getArticleShowList(Integer categoryId, Integer tagId, Integer condition, Integer page);

    /**
     * 前台 获取文章详细信息
     * @param id 文章id
     * @return 文章具体数据
     */
    ArticleShowVo getArticleShowById(Long id);

    /**
     * 根据用户id 获取用户发布的文章列表
     * @param id 用户id
     * @return 用户发布的文章列表
     */
    List<Article> getArticleListById(Long id);

    /**
     * 根据用户id 分页获取用户发表的文章列表
     * @param id 用户id
     * @param page 当前页数
     * @return 用户发表的文章列表
     */
    List<ArticleShowVo> getUserArticleShowList(Long id, Integer page);

    /**
     * 根据关键字 获取文章列表
     * @param key 关键字
     * @param page 当前页
     * @return 文章列表
     */
    List<ArticleShowVo> getArticleShowByKey(String key,Integer page);

    /**
     * 根据标签id 获取文章列表
     * @param id 标签id
     * @param page 当前页
     * @return 搜索结果
     */
    List<ArticleShowVo> getArticleShowByTagId(Integer id, Integer page);

    /**
     * 随机获取缓存中5个保存的文章 缓存中存储100个最新文章
     * @return 随机文章列表
     */
    List<CurrencyShowVo> randomArticleShow();

    /**
     * 删除文章
     * @param id 文章id
     * @return 删除结果
     */
    int deleteArticle(Long id);

    /**
     * 获取编辑文章
     * @param map 接收要修改的文章id
     * @return 文章内容
     */
    CurrencyShowVo getEditArticle(Map<String, String> map);

    /**
     * 修改文章
     * @param articleEditVo 要修改的文章信息
     * @return 修改结果
     */
    int renewArticle(ArticleEditVo articleEditVo);

    /**
     * 定时更新 文章表里面的点赞数
     */
    void fixedTimeUpdateThumbsUp();
}
