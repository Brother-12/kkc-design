package com.kerco.kkc.community.entity.vo;

import com.kerco.kkc.common.entity.UserKeyTo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 前台 评论类 显示具体的关键信息
 */
@ToString
@Data
public class CommentVo {
    //评论表的id
    private Long id;

    //评论用户的信息
    private UserKeyTo userInfo;

    //评论的内容
    private String commentContent;

    //二级评论需要指定的 评论id
    private Long parentId;

    //回复人的名字
    private String replyUsername;

    //评论时间
    private LocalDateTime createTime;

    //二级评论的 评论数据
    private List<CommentVo> subComment;
}
