package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.entity.vo.CategoryTreeVo;
import com.kerco.kkc.community.entity.vo.CategoryVo;
import com.kerco.kkc.community.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分页 获取分类数据
     * @param page 当前页数
     * @return 使用统一分页工具存储分类数据
     */
    PageUtils getCategoryList(Integer page);

    /**
     * 根据id 获取分类信息
     * @param id
     * @return
     */
    Category getCategoryById(Long id);

    /**
     * 根据id更新分类信息
     * @param category
     * @return
     */
    int updateCategoryById(Category category);

    /**
     * 根据id 删除分类信息
     * @param id
     * @return
     */
    int deleteCategoryById(Long id);

    /**
     * 创建分类
     * @param category 分类信息
     * @return 创建结果
     */
    int createNewCategory(Category category);

    /**
     * 获取所有分类数据 转为Map
     * 只存储 id、name
     * @return 返回所有分类数据
     */
    Map<Integer,String> getAllCategoryVoDataToMap();

    /**
     * 获取所有分类数据
     * 只存储 id、name
     * @return 返回所有分类数据
     */
    List<CategoryVo> getAllCategoryVoData();

    /**
     * 获取 分类树
     * @return
     */
    List<CategoryTreeVo> getCategoryTree();
}
