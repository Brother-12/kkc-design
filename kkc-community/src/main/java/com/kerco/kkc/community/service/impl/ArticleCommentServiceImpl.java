package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.ArticleComment;
import com.kerco.kkc.community.entity.ArticleContent;
import com.kerco.kkc.community.mapper.ArticleCommentMapper;
import com.kerco.kkc.community.service.ArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {
}
