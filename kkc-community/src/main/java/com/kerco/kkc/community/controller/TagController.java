package com.kerco.kkc.community.controller;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.Tag;
import com.kerco.kkc.community.service.TagService;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/community/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 分页获取所有标签信息
     *
     * 请求参数解锁currentPage和key
     * currentPage：当前页数，不是必须
     * key：搜索关键字，不是必须
     * @return 分页 标签信息
     */
    @GetMapping("/list")
    public CommonResult getTagList(@RequestParam(value = "currentPage",required = false) Integer page,
                                    @RequestParam(value = "key",required = false) String key){
        PageUtils userList = tagService.getTagList(page,key);

        CommonResult<PageUtils> result = CommonResult.success(userList);
        return result;
    }

    /**
     * 获取 标签信息
     *
     * @return 返回标签信息
     */
    @GetMapping("/getTag")
    public CommonResult<Tag> getTagById(@RequestParam("id") Long id){
        if(id == null){
            // 这里需要一个全局错误代码来代替10000
            return CommonResult.error(10000,"标签id为空");
        }

        Tag tag = tagService.getTagById(id);

        CommonResult<Tag> result = CommonResult.success(tag);
        return result;
    }

    /**
     * 更新 标签信息
     * @param tag 待更新的信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public CommonResult updateTagById(@RequestBody Tag tag){
        int i = tagService.updateTagById(tag);

        return CommonResult.success("更新成功");
    }

    /**
     * 根据id删除标签
     * @param id 标签id
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public CommonResult deleteTagById(@PathVariable("id") Long id){
        int result = tagService.deleteTagById(id);

        return CommonResult.success(result);
    }

    @PostMapping("/createTag")
    public CommonResult createUser(@RequestBody Tag tag){
        int result = tagService.createNewTag(tag);

        return CommonResult.success("创建成功");
    }
}
