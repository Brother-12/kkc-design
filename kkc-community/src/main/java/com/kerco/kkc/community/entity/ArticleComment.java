package com.kerco.kkc.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
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

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
            "id=" + id +
            ", articleId=" + articleId +
            ", commentId=" + commentId +
            ", parentId=" + parentId +
            ", replyId=" + replyId +
            ", commentContent=" + commentContent +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
