package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("community_article_comment")
@ApiModel(value = "ArticleComment对象", description = "")
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("评论文章id")
    private Long articleId;

    @ApiModelProperty("评论人id")
    private Long commentId;

    @ApiModelProperty("二级评论 记录一级评论下的回复")
    private Long parentId;

    @ApiModelProperty("回复人id")
    private Long replyId;

    @ApiModelProperty("评论的内容")
    private String commentContent;

    @ApiModelProperty("评论的状态 0:正常显示 1:删除")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
