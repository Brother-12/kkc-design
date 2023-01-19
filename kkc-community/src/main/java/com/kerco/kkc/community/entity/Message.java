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
@TableName("community_message")
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户消息表")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("发起人id")
    private Long clientId;

    @ApiModelProperty("发起人名字")
    private String clientUsername;

    @ApiModelProperty("发起人头像")
    private String clientAvatar;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("类型 0:文章 1:问答")
    private Integer optType;

    @ApiModelProperty("操作 0:点赞 1:评论")
    private Integer optOperation;

    @ApiModelProperty("文章/问答id")
    private Long optId;

    @ApiModelProperty("文章/问答标题")
    private String optTitle;

    @ApiModelProperty("状态 0:启用 1:禁用")
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
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }
    public String getClientAvatar() {
        return clientAvatar;
    }

    public void setClientAvatar(String clientAvatar) {
        this.clientAvatar = clientAvatar;
    }
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }
    public Integer getOptOperation() {
        return optOperation;
    }

    public void setOptOperation(Integer optOperation) {
        this.optOperation = optOperation;
    }
    public Long getOptId() {
        return optId;
    }

    public void setOptId(Long optId) {
        this.optId = optId;
    }
    public String getOptTitle() {
        return optTitle;
    }

    public void setOptTitle(String optTitle) {
        this.optTitle = optTitle;
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
        return "Message{" +
            "id=" + id +
            ", clientId=" + clientId +
            ", clientUsername=" + clientUsername +
            ", clientAvatar=" + clientAvatar +
            ", authorId=" + authorId +
            ", optType=" + optType +
            ", optOperation=" + optOperation +
            ", optId=" + optId +
            ", optTitle=" + optTitle +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
