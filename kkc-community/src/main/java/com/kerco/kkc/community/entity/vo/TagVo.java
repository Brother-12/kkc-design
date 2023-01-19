package com.kerco.kkc.community.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 后台 标签列表 实体类
 */
@ToString
@Data
public class TagVo {
    //标签id
    private Integer id;

    //标签名
    private String tagName;

    //分类id
    private Integer categoryId;

    //分类名
    private String categoryName;

    //文章/问答 引用次数
    private Integer refCount;

    //排序
    private Integer sort;

    //状态
    private Integer status;

    //创建事件
    private LocalDateTime createTime;
}
