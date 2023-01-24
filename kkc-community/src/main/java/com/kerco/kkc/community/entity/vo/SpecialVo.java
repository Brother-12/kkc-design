package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 特殊的 展示类(精选)
 */
@ToString
@Data
public class SpecialVo {
    //作者id
    private Long authorId;

    //作者名字
    private String authorUsername;

    //作者头像
    private String authorAvatar;

    //文章/问答的 id
    private String specialId;

    //文章/问答的 标题
    private String specialTitle;

    //文章/问答的 浏览数
    private Integer views;
}
