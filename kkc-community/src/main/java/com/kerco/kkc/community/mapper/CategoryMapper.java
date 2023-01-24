package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.community.entity.vo.CategoryTreeVo;
import com.kerco.kkc.community.entity.vo.CategoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 根据id更新分类信息
     * @param category 分类信息
     * @return 0：没有更新 或 1：已更新
     */
    int updateCategoryById(Category category);

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    int deleteCategoryById(Long id);

    /**
     * 创建分类
     * @param category
     * @return
     */
    int createNewCategory(Category category);

    /**
     * 创建分类之前的检查
     * @param name
     * @return
     */
    int createCategoryCheck(String name);

    /**
     * 获取所有分类数据
     * 只需要id、name
     * @return 返回所有分类数据
     */
    List<CategoryVo> getAllCategoryVoData();

    /**
     * 获取分类树
     * @return 分类树
     */
    List<CategoryTreeVo> getCategoryTree();

}
