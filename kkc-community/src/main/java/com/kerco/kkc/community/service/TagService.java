package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.entity.vo.TagTreeVo;
import com.kerco.kkc.community.utils.PageUtils;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface TagService extends IService<Tag> {

    /**
     * 分页获取所有标签信息
     *
     * 请求参数解锁currentPage和key
     * currentPage：当前页数，不是必须
     * key：搜索关键字，不是必须
     * @return 分页 标签信息
     */
    PageUtils getTagList(Integer page, String key);

    /**
     * 获取 标签信息
     *
     * @return 返回标签信息
     */
    Tag getTagById(Long id);

    /**
     * 更新 标签信息
     * @param tag 待更新的标签数据
     * @return 更新结果
     */
    int updateTagById(Tag tag);

    /**
     * 根据id 删除标签
     * @param id 用户id
     * @return 删除结果
     */
    int deleteTagById(Long id);

    /**
     * 创建标签
     * @param tag 标签信息
     * @return 创建结果
     */
    int createNewTag(Tag tag);

    /**
     * 获取所有 启用的标签(id、标签名)
     * @return 所有关键标签信息
     */
    List<TagTreeVo> getKeyTagList();

    /**
     * 对引用的标签 的refCount 进行+1
     * @param tagIds 若干个标签id
     * @param categoryId 分类id
     */
    int incrRefCount(Integer categoryId,List<Integer> tagIds);

    /**
     * 根据分类id获取关联的标签id
     * @param categoryId 分类id
     * @return 标签id
     */
    List<String> getTagListByCategoryId(Integer categoryId);
}
