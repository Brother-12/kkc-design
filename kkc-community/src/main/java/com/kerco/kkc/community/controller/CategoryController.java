package com.kerco.kkc.community.controller;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.Category;
import com.kerco.kkc.community.entity.vo.CategoryTreeVo;
import com.kerco.kkc.community.entity.vo.CategoryVo;
import com.kerco.kkc.community.service.CategoryService;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  分类控制器
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/community/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页获取 分类数据
     * @param page 当前页数
     * @return 分页后的数据
     */
    @GetMapping("/list")
    public CommonResult getCategoryList(@RequestParam(value = "currentPage",required = false) Integer page){
        PageUtils categoryList = categoryService.getCategoryList(page);

        return CommonResult.success(categoryList);
    }

    /**
     * 获取标签编辑时 所属分类的数据
     * @return
     */
    @GetMapping("/select/category")
    public CommonResult getSelectCategoryList(){
        List<CategoryVo> allCategoryVoData = categoryService.getAllCategoryVoData();

        return CommonResult.success(allCategoryVoData);
    }

    /**
     * 获取 分类树
     * @return
     */
    @GetMapping("/tree")
    public CommonResult getCategoryTree(){
        List<CategoryTreeVo> treeList = categoryService.getCategoryTree();

        return CommonResult.success(treeList);
    }

    /**
     * 根据id获取分类信息
     * @return 分类信息
     */
    @GetMapping("/getCategory")
    public CommonResult<Category> getCategoryById(@RequestParam("id") Long id){
        if(id == null){
            // 这里需要一个全局错误代码来代替10000
            return CommonResult.error(10000,"分类id为空");
        }

        Category category = categoryService.getCategoryById(id);

        return CommonResult.success(category);
    }

    /**
     * 根据id更新分类信息
     * @param category 要更新的分类信息
     * @return 更新后的结果
     */
    @PostMapping("/update")
    public CommonResult updateCategoryById(@RequestBody Category category){
        int i = categoryService.updateCategoryById(category);

        return CommonResult.success("更新成功");
    }

    /**
     * 根据id删除分类信息
     * 将status=2
     * @param id 用户id
     * @return
     */
    @PostMapping("/delete/{id}")
    public CommonResult deleteCategoryById(@PathVariable("id") Long id){
        int result = categoryService.deleteCategoryById(id);

        return CommonResult.success(result);
    }

    /**
     * 创建分类
     * @param category
     * @return
     */
    @PostMapping("/createCategory")
    public CommonResult createUser(@RequestBody Category category){
        categoryService.createNewCategory(category);

        return CommonResult.success("创建成功");
    }
}
