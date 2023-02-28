package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.mapper.ArticleContentMapper;
import com.kerco.kkc.community.service.ArticleContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentService {

    @Autowired
    private ArticleContentMapper contentMapper;

    /**
     * 根据文章id 获取文章内容
     * @param articleId 文章id
     * @return 文章内容
     */
    @Override
    public ArticleContent getArticleContentById(Long articleId) {
        return this.getById(articleId);
    }

    /**
     * 保存文章内容
     * @param id 文章id
     * @param content 文章内容
     */
    @Override
    public void saveArticleContent(Long id, String content) {
        ArticleContent content1 = new ArticleContent();
        content1.setMdContent(content);
        content1.setId(id);

        //第一张图片处理
        String regex = "https://kkc-picture.oss-cn-shenzhen.aliyuncs.com/(.*?)(\\.jpg|\\.jpeg|\\.png)";
        Pattern p = Pattern.compile(regex);
        Matcher ma = p.matcher(content);

        //如果该文章内有图片，则
        if(ma.find()){
            content1.setFirstImg(ma.group());
        }

        //文本处理
        String regexs = "(#|>|\\*\\*|`| |!\\[.*?\\]|\\(https.*?\\))";
        Pattern compile = Pattern.compile(regexs);
        Matcher matcher = compile.matcher(content);
        //处理md文档的所有特殊符号，只保留文本
        if(matcher.find()){
            content1.setParseContent(matcher.replaceAll(""));
        }else{
            content1.setParseContent(content);
        }

        int i = contentMapper.saveArticleContent(content1);
        if(i > 0){
            System.out.println("保存成功");
        }
    }

    /**
     * 修改文章内容
     * @param id 文章id
     * @param content 文章内容
     */
    @Override
    public int renewArticleContent(Long id, String content) {
        ArticleContent content1 = new ArticleContent();
        content1.setMdContent(content);
        content1.setId(id);

        //第一张图片处理
        String regex = "https://kkc-picture.oss-cn-shenzhen.aliyuncs.com/(.*?)(\\.jpg|\\.jpeg|\\.png)";
        Pattern p = Pattern.compile(regex);
        Matcher ma = p.matcher(content);

        //如果该文章内有图片，则
        if(ma.find()){
            content1.setFirstImg(ma.group());
        }

        //文本处理
        String regexs = "(#|>|\\*\\*|`| |!\\[.*?\\]|\\(https.*?\\))";
        Pattern compile = Pattern.compile(regexs);
        Matcher matcher = compile.matcher(content);
        //处理md文档的所有特殊符号，只保留文本
        if(matcher.find()){
            content1.setParseContent(matcher.replaceAll(""));
        }else{
            content1.setParseContent(content);
        }

        return contentMapper.renewArticleContent(content1);
    }
}
