package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.community.entity.Tag;
import com.kerco.kkc.community.entity.vo.CategoryVo;
import com.kerco.kkc.community.entity.vo.TagTreeVo;
import com.kerco.kkc.community.entity.vo.TagVo;
import com.kerco.kkc.community.mapper.TagMapper;
import com.kerco.kkc.community.service.CategoryService;
import com.kerco.kkc.community.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagMapper tagMapper;

    /**
     * 分页获取所有标签信息
     *
     * 请求参数解锁currentPage和key
     * currentPage：当前页数，不是必须
     * key：搜索关键字，不是必须
     * @return 分页 标签信息
     */
    @Override
    public PageUtils getTagList(Integer currentPage, String key) {
        Page<Tag> page = null;
        //这里使用条件构造器来构建sql语句，配合我们使用的分页插件，生成分页数据
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        //对数据进行排序操作，首先对 分类id进行总体排序，然后内容在根据sort进行排序
        wrapper.orderByAsc("category_id")
            .orderByAsc("sort");

        if(StringUtils.hasLength(key)){
            wrapper = new QueryWrapper<Tag>().like("tag_name", key);
        }

        //如果当前页为空，则从第一页开始查找
        if(currentPage == null){
            page = this.page(new Page<Tag>(1, 7), wrapper);
        }else{
            page = this.page(new Page<Tag>(currentPage, 7), wrapper);
        }

        //获取 分类 部分信息 所有数据
        Map<Integer, String> categoryMap = categoryService.getAllCategoryVoDataToMap();
        //获取分页后的标签数据
        List<Tag> records = page.getRecords();
        //将Tag转为TagVo，因为需要存储分类名
        List<TagVo> result = records.stream().map(v -> {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(v, tagVo);
            //去categoryMap搜索，将结果进行保存
            tagVo.setCategoryName(categoryMap.get(v.getCategoryId()));
            return tagVo;
        }).collect(Collectors.toList());

        return new PageUtils(result,(int) page.getTotal(), (int) page.getSize(),(int) page.getCurrent());
    }

    /**
     * 获取 标签信息
     *
     * @return 返回标签信息
     */
    @Override
    public Tag getTagById(Long id) {
        return this.getById(id);
    }

    /**
     * 更新 标签信息
     * @param tag 待更新的标签数据
     * @return 更新结果
     */
    @Override
    public int updateTagById(Tag tag) {
        //TODO 更新之前需要进行校验
        return tagMapper.updateTagById(tag);
    }

    /**
     * 根据id 删除标签
     * @param id 用户id
     * @return 删除结果
     */
    @Override
    public int deleteTagById(Long id) {
        return tagMapper.deleteTagById(id);
    }

    /**
     * 创建标签
     * @param tag 标签信息
     * @return 创建结果
     */
    @Override
    public int createNewTag(Tag tag) {

        //创建标签之前需要校验
        int result = tagMapper.createBeforeCheck(tag.getTagName());

        //1.判断标签名是否已经存在
        if(result > 0){
            throw new RuntimeException("该标签名已经存在");
        }

        //2.所属分类id是否为空
        if(Objects.isNull(tag.getCategoryId())){
            throw new RuntimeException("所属分类不能为空");
        }

        return tagMapper.createTag(tag);
    }

    /**
     * 获取所有 启用的标签(id、标签名)
     * @return 所有关键标签信息
     */
    @Override
    public List<TagTreeVo> getKeyTagList() {
        return tagMapper.getKeyTagList();
    }
}
