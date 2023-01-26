package com.kerco.kkc.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.kerco.kkc.common.entity.UserKeyTo;
import com.kerco.kkc.member.entity.User;
import com.kerco.kkc.member.mapper.UserMapper;
import com.kerco.kkc.member.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.member.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    @Override
    public PageUtils getUserList(Integer currentPage,String key) {
        Page<User> page = null;
        //这里使用条件构造器来构建sql语句，配合我们使用的分页插件，生成分页数据
        QueryWrapper<User> wrapper = null;

        if(StringUtils.hasLength(key)){
            wrapper = new QueryWrapper<User>().like("username", key);
        }

        //如果当前页为空，则从第一页开始查找
        if(currentPage == null){
            page = this.page(new Page<User>(1, 7), wrapper);
        }else{
            page = this.page(new Page<User>(currentPage, 7), wrapper);
        }
        return new PageUtils(page);
    }

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 指定用户信息
     */
    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据用户id更新用户信息
     * @param user 用户部分信息
     * @return 更新后的结果 数字 0:没更新 1:更新
     */
    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    /**
     * 删除用户 修改status 字段为2
     * @param id 用户id
     * @return 删除后的结果
     */
    @Override
    public int deleteUserById(Long id) {
        return userMapper.deleteUserById(id);
    }

    /**
     * 创建用户
     * @param user 部分参数（用户名、密码、邮箱）
     * @return
     */
    @Override
    public int createNewUser(User user) {
        user.setNickname(user.getUsername());

        int beforeCheck = 0;
        //TODO 在创建之前我们需要先校验 用户名和邮箱是否已经存在
        beforeCheck = userMapper.createBeforeCheck(user.getEmail(), null);
        if(beforeCheck > 0){
            //如果大于0，则表示已经有该邮箱，直接返回异常
            throw new RuntimeException("邮箱已经存在，请重新注册");
        }
        beforeCheck = userMapper.createBeforeCheck(null, user.getUsername());
        if(beforeCheck > 0){
            //如果大于0，则表示已经有该用户名，直接返回异常
            throw new RuntimeException("用户名已经存在，请更换其他的");
        }

        return userMapper.createNewUser(user);
    }

    /**
     * 根据用户名和密码获取用户信息
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username,password);
    }

    /**
     * 根据 若干个用户id 获取用户关键信息
     * @param list 用户id集合
     * @return 用户关键信息
     */
    @Override
    public Map<Long, UserKeyTo> getUserListByIds(List<Long> list) {
        List<UserKeyTo> result = userMapper.getUserListByIds(list);

        if(Objects.isNull(result)){
            throw new RuntimeException("用户信息获取异常..");
        }

        return result.stream().collect(Collectors.toMap(UserKeyTo::getId, v -> v));
    }
}
