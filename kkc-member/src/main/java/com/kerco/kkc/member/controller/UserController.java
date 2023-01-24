package com.kerco.kkc.member.controller;

import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.member.entity.User;
import com.kerco.kkc.member.service.UserService;
import com.kerco.kkc.member.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/member/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页获取所有用户信息
     *
     * 请求参数解锁currentPage和key
     * currentPage：当前页数，不是必须
     * key：搜索关键字，不是必须
     * @return 分页 用户列表信息
     */
    @GetMapping("/list")
    public CommonResult getUserList(@RequestParam(value = "currentPage",required = false) Integer page,
                                    @RequestParam(value = "key",required = false) String key){
        PageUtils userList = userService.getUserList(page,key);

        CommonResult<PageUtils> result = CommonResult.success(userList);
        return result;
    }

    /**
     * 获取用户个人信息
     * @return 所有用户列表信息
     */
    @GetMapping("/getUser")
    public CommonResult<User> getUserById(@RequestParam("id") Long id){
        if(id == null){
            return CommonResult.error(10000,"用户id为空");
        }

        User user = userService.getUserById(id);

        CommonResult<User> result = CommonResult.success(user);
        return result;
    }

    /**
     * 获取用户个人信息
     * @return 所有用户列表信息
     */
    @GetMapping("/in/getUser")
    public CommonResult<UserTo> getUserByIdToWrite(@RequestParam("id") Long id){
        if(id == null){
            return CommonResult.error(10000,"用户id为空");
        }

        User user = userService.getUserById(id);
        UserTo userTo = new UserTo();
        BeanUtils.copyProperties(user,userTo);

        CommonResult<UserTo> result = CommonResult.success(userTo);
        return result;
    }

    @PostMapping("/update")
    public CommonResult updateUserById(@RequestBody User user){
        int i = userService.updateUserById(user);

        return CommonResult.success("更新成功");
    }

    @PostMapping("/delete/{id}")
    public CommonResult deleteUserById(@PathVariable("id") Long id){
        int result = userService.deleteUserById(id);

        return CommonResult.success(result);
    }

    @PostMapping("/createUser")
    public CommonResult createUser(@RequestBody User user){
        int result = userService.createNewUser(user);

        return CommonResult.success("创建成功");
    }
}
