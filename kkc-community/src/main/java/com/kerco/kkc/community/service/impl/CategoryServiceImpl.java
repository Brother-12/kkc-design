package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.community.entity.Article;
import com.kerco.kkc.community.entity.Category;
import com.kerco.kkc.community.entity.vo.CategoryTreeVo;
import com.kerco.kkc.community.entity.vo.CategoryVo;
import com.kerco.kkc.community.mapper.CategoryMapper;
import com.kerco.kkc.community.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分页 获取分类数据
     * @param page 当前页数
     * @return 使用统一分页工具存储分类数据
     */
    @Override
    public PageUtils getCategoryList(Integer page) {
        Page<Category> result = null;
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        //对sort字段进行排序
        wrapper.orderByAsc("sort");

        //如果当前页为空，则从第一页开始查找
        if(page == null){
            result = this.page(new Page<Category>(1, 7), wrapper);
        }else{
            result = this.page(new Page<Category>(page, 7), wrapper);
        }

        return new PageUtils(result);
    }

    /**
     * 根据id 获取分类信息
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据id更新分类信息
     * @param category
     * @return
     */
    @Override
    public int updateCategoryById(Category category) {
        //TODO 更新前 我们需要进行校验工作

        return categoryMapper.updateCategoryById(category);
    }

    @Override
    public int deleteCategoryById(Long id) {
        return categoryMapper.deleteCategoryById(id);
    }

    /**
     * 创建分类
     * @param category 分类信息
     * @return 创建结果
     */
    @Override
    public int createNewCategory(Category category) {
        //TODO 创建之前需要先校验
        int count = categoryMapper.createCategoryCheck(category.getName());
        if(count > 0){
            //这里需要做全局异常常量
            throw new RuntimeException("该分类名已存在，请重新换一个");
        }

        return categoryMapper.createNewCategory(category);
    }

    /**
     * 获取所有分类数据 转为Map
     * 只存储 id、name
     * @return 返回所有分类数据
     */
    @Override
    public Map<Integer,String> getAllCategoryVoDataToMap() {
        List<CategoryVo> allCategoryVoData = categoryMapper.getAllCategoryVoData();
        return allCategoryVoData.stream().parallel().collect(Collectors.toMap(CategoryVo::getId, CategoryVo::getName));
    }

    /**
     * 获取所有分类数据
     * 只存储 id、name
     * @return 返回所有分类数据
     */
    @Override
    public List<CategoryVo> getAllCategoryVoData() {
        return categoryMapper.getAllCategoryVoData();
    }

    /**
     * 获取分类树
     * @return 分类树
     */
    @Override
    public List<CategoryTreeVo> getCategoryTree() {
        List<CategoryTreeVo> categoryTree = categoryMapper.getCategoryTree();
        return categoryTree;
    }
}
