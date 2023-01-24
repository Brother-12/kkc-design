package com.kerco.kkc.community.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 将文章/问答 通用展示的类进行整合
 */
@Data
@ToString
public class CurrencyShowVo implements Serializable {
    private static final long serialVersionUID = 1L;
    //文章 or 问答id
    private Long id;
    //文章 or 问答标题
    private String title;
    //作者id
    private Long authorId;
    //作者头像
    private String authorAvatar;
    //作者名字
    private String authorUsername;
    //浏览数
    private Integer views;
    //发布日期
    private LocalDateTime publishTime;
    //文本
    private String content;
    //关联的 标签id
    private transient String tagIds;
    //关联的 标签列表
    private List<TagTreeVo> tagList;

    private Integer thumbsup;
}
