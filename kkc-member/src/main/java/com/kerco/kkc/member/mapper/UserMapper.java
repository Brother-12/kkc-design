package com.kerco.kkc.member.mapper;

import com.kerco.kkc.common.entity.UserKeyTo;
import com.kerco.kkc.member.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.member.entity.qo.CountQo;
import com.kerco.kkc.member.entity.vo.UserDetailVo;
import com.kerco.kkc.member.entity.vo.UserInfoVo;
import com.kerco.kkc.member.entity.vo.UserSimpleShowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据id更新用户信息
     * @param user
     * @return
     */
    int updateUserById(User user);

    /**
     * 根据id 删除用户 更新status字段为 2
     * @param id
     * @return
     */
    int deleteUserById(@Param("id") Long id);

    List<User> getUserByKey(@Param("key") String key);

    /**
     * 创建新用户
     * @param user 用户信息
     * @return 创建结果
     */
    int createNewUser(User user);

    /**
     * 创建用户之前的校验
     * @param email
     * @param username
     * @return
     */
    int createBeforeCheck(@Param("email") String email,@Param("username") String username);
    /**
     * 根据用户名和密码获取用户信息
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据 若干个用户id 获取用户关键信息
     * @param list 用户id集合
     * @return 用户关键信息
     */
    List<UserKeyTo> getUserListByIds(@Param("ids") List<Long> list);

    /**
     * 获取用户简单的信息
     * @param id 用户id
     * @return 用户信息
     */
    UserSimpleShowVo getUserSimpleShowById(@Param("id") Long id);

    /**
     * 获取当前用户的 关注数和粉丝数
     * @param id
     * @return
     */
    List<CountQo> getFollowerCountAndFollowedCountById(@Param("id") Long id);

    /**
     * 获取用户的 关注用户列表
     * @param id 用户id
     * @return 关注用户列表
     */
    List<UserInfoVo> getUserFollowList(@Param("id") Long id);

    /**
     * 获取用户的 粉丝列表
     * @param id 用户id
     * @return 粉丝列表
     */
    List<UserInfoVo> getUserFollowedList(@Param("id") Long id);

    /**
     * 获取用户详细信息
     * @param id 用户id
     * @return 用户详细信息
     */
    UserDetailVo getUserDetailById(@Param("id") Long id);

    /**
     * 用户 修改自己的个人信息
     * @param user 用户信息
     * @return 修改结果
     */
    int updateUserInfoByUser(User user);
}
