package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.entity.QuestionContent;
import com.kerco.kkc.community.mapper.QuestionContentMapper;
import com.kerco.kkc.community.service.QuestionContentService;
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
public class QuestionContentServiceImpl extends ServiceImpl<QuestionContentMapper, QuestionContent> implements QuestionContentService {

    @Autowired
    private QuestionContentMapper contentMapper;

    /**
     * 根据问答id获取内容
     * @param questionId 问答id
     * @return
     */
    @Override
    public QuestionContent getQuestionContentById(Long questionId) {
        return this.getById(questionId);
    }

    /**
     * 保存问答内容
     * @param id 问答id
     * @param content 问答内容
     */
    @Override
    public void saveQuestion(Long id, String content) {
        QuestionContent content1 = new QuestionContent();
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

        int i = contentMapper.saveQuestionContent(content1);
        if(i > 0){
            System.out.println("保存成功");
        }
    }
}
