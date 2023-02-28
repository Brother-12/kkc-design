package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Data
public class ArticleEditVo {

    @NotNull(message = "文章id不能为空")
    private Long id;
    //    作者id
    @NotNull(message = "用户id不能为空")
    private Long authorId;
    //    文章标题
    @Length(max = 70,message = "标题最多70个字")
    @NotBlank(message = "文章标题不能为空")
    private String title;
    //    文章内容
    @NotBlank(message = "文章内容不能为空")
    private String content;
    //    文章分类id
    @NotNull(message = "文章分类id不能为空")
    private Integer categoryId;
    //    标签id：多个
    @NotEmpty(message = "文章标签id不能为空")
    private List<Integer> tagIds;
}
