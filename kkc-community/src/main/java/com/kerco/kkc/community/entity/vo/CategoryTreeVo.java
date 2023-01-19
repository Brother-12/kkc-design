package com.kerco.kkc.community.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 后台： 树形显示 使用
 * 前台： 分类、标签 显示也能使用
 */
@ToString
@Data
public class CategoryTreeVo {

    //分类id
    private Integer id;
    //分类名
    private String label;
    //被关联的 所有标签名字
    private List<TagTreeVo> children;
}
