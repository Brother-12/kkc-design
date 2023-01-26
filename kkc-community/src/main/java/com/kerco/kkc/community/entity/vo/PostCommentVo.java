package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 前台 发表评论 接收的类
 */
@ToString
@Data
public class PostCommentVo {
    //文章/问答的 id
    @NotNull(message = "id不能为空")
    private Long id;

    //评论人的id
    @NotNull(message = "commentId不能为空")
    private Long commentId;

    //回复人的id
    private Long replyId = 0L;

    //二级评论需要指定的 评论id
    private Long parentId = 0L;

    //评论内容
    @Length(max = 200,message = "最多只能输入200个字")
    @NotBlank(message = "评论的内容不能为空...")
    private String commentContent;
}
