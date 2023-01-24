package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户文章提交类
 */
@ToString
@Data
public class QuestionWriteVo {

    //    作者id
    @NotNull(message = "用户id不能为空")
    private Long authorId;
    //    问答标题
    @Length(max = 60,message = "标题最多60个字")
    @NotBlank(message = "问答标题不能为空")
    private String title;
    //    问答内容
    @NotBlank(message = "问答内容不能为空")
    private String content;
    //    问答分类id
    @NotNull(message = "问答分类id不能为空")
    private Integer categoryId;
    //    问答标签id：多个
    @NotEmpty(message = "问答标签id不能为空")
    private List<Integer> tagIds;
}
