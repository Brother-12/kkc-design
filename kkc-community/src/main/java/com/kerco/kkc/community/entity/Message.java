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

    @ApiModelProperty("接收消息 用户id")
    private Long userId;

    @ApiModelProperty("类型 0:文章 1:问答")
    private Integer optType;

    @ApiModelProperty("操作 0:点赞 1:评论")
    private Integer optOperation;

    @ApiModelProperty("文章/问答id")
    private Long optId;

    @ApiModelProperty("文章/问答标题")
    private String optTitle;

    @ApiModelProperty("标记 0:未读 1:已读")
    private Integer mark;

    @ApiModelProperty("状态 0:启用 1:禁用")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
