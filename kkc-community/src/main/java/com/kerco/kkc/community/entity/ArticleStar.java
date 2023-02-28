package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-02-23
 */
@Data
@TableName("community_article_star")
@ApiModel(value = "ArticleStar对象", description = "")
public class ArticleStar implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("点赞表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章id")
    private Long articleId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("0：点赞，1：取消点赞")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
