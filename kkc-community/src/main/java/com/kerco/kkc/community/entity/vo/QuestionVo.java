package com.kerco.kkc.community.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
public class QuestionVo {
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

    @ApiModelProperty("文章的标签 用逗号隔开如: 1,5,8")
    private List<String> tagNames;

    @ApiModelProperty("文章状态 0:正常开放 1:删除")
    private Integer status;

    @ApiModelProperty("排序 100:默认 1:置顶")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
