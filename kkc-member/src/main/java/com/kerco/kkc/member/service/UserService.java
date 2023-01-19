package com.kerco.kkc.member.service;

import com.kerco.kkc.member.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.member.utils.PageUtils;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface UserService extends IService<User> {
    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    PageUtils getUserList(Integer currentPage,String key);

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 指定用户信息
     */
    User getUserById(Long id);

    /**
     * 根据用户id更新用户信息
     * @param user 用户部分信息
     * @return 更新后的结果 数字 0:没更新 1:更新
     */
    int updateUserById(User user);

    /**
     * 删除用户
     * @param id 用户id
     * @return 删除后的结果
     */
    int deleteUserById(Long id);

    /**
     * 新建一个用户
     * @param user 部分参数（用户名、密码、邮箱）
     * @return
     */
    int createNewUser(User user);
}
