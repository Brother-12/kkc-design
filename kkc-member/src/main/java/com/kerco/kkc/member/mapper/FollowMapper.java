package com.kerco.kkc.member.mapper;

import com.kerco.kkc.member.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    /**
     * 检查用户是否有关注
     * @param userId 登陆用户id
     * @param followId 其他用户id
     * @return
     */
    Follow checkUserFollow(@Param("userId") Long userId, @Param("followId") Long followId);

    /**
     * 插入用户关注信息
     */
    int userConfirmFollow(Follow follow);

    /**
     * 更新用户关注的状态
     * @param id 关注表id
     * @param status 关注状态
     */
    int updateFollowStatus(@Param("id") Long id, @Param("status") int status);
}
