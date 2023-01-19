package com.kerco.kkc.community.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 分类关键信息类
 * 在下拉框 选择分类时使用
 */
@ToString
@Data
public class CategoryVo {
    //分类id
    private Integer id;
    //分类名
    private String name;
}
