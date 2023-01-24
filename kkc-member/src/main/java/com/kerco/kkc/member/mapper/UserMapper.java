package com.kerco.kkc.member.mapper;

import com.kerco.kkc.member.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
