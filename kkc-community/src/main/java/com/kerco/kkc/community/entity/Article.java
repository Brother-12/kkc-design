package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@ToString
@Data
@TableName("community_article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("作者头像")
    private String authorAvatar;

    @ApiModelProperty("作者名字")
    private String authorUsername;

    @ApiModelProperty("点赞数")
    private Integer thumbsup;

    @ApiModelProperty("浏览数")
    private Integer views;

    @ApiModelProperty("审核状态 0:审核中 1:已审核")
    private Integer examination;

    @ApiModelProperty("官方 0:不是 1:是")
    private Integer official;

    @ApiModelProperty("置顶 0:不是 1:是")
    private Integer top;

    @ApiModelProperty("精华 0:不是 1:是")
    private Integer essence;

    @ApiModelProperty("文章的标签 用逗号隔开如: 1,5,8")
    private String tagIds;

    @ApiModelProperty("文章状态 0:正常开放 1:删除")
    private Integer status;

    @ApiModelProperty("排序 100:默认 1:置顶")
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
