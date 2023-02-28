package com.kerco.kkc.community.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageVo {
    @ApiModelProperty("用户消息表")
    private Long id;

    @ApiModelProperty("发起人id")
    private Long clientId;

    @ApiModelProperty("发起人名字")
    private String clientUsername;

    @ApiModelProperty("发起人头像")
    private String clientAvatar;

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
}
