package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.community.entity.vo.TagTreeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据id更新标签信息
     * @param tag
     * @return
     */
    int updateTagById(Tag tag);

    /**
     * 根据id删除标签 将status改为2
     * @param id
     * @return
     */
    int deleteTagById(Long id);

    /**
     * 创建标签
     * @param tag 标签信息
     * @return 创建结果
     */
    int createTag(Tag tag);

    /**
     * 检查标签名是否已经存在
     * @param tagName 标签名
     * @return 检索结果
     */
    int createBeforeCheck(String tagName);

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
    int incrRefCount(@Param("categoryId") Integer categoryId, @Param("tagIds") List<Integer> tagIds);

    List<String> getTagListByCategoryId(Integer categoryId);
}
