package com.kerco.kkc.member.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.member.entity.vo.UserDetailVo;
import com.kerco.kkc.member.entity.vo.UserInfoVo;
import com.kerco.kkc.member.entity.vo.UserSimpleShowVo;
import com.kerco.kkc.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserFrontController {

    @Autowired
    private UserService userService;

    /**
     * 前台：获取用户简单的信息
     * 该方法属于公共方法，能直接获取
     * @param id 用户id
     * @return 用户简单的信息
     */
    @GetMapping("/show/getUser")
    public CommonResult getUserSimpleById(@RequestParam("id") Long id){
        UserSimpleShowVo userSimpleShowVo = userService.getUserSimpleShowById(id);

        return CommonResult.success(userSimpleShowVo);
    }

    /**
     * 前台：获取用户简单的信息
     * @param id 用户id
     * @return 用户简单的信息
     */
    @GetMapping("/show/getUserDetail")
    public CommonResult getUserDetailById(@RequestParam("id") Long id, HttpServletRequest request){
        UserDetailVo userDetailVo = userService.getUserDetailById(id,request);
        return CommonResult.success(userDetailVo);
    }

    /**
     * 前台：修改用户的信息
     * @return 修改结果
     */
    @PostMapping("/show/update")
    public CommonResult updateUserDetail(@Valid  @RequestBody UserSimpleShowVo userSimpleShowVo, HttpServletRequest request){
        int i = userService.updateUserDetail(userSimpleShowVo, request);
        if(i > 0){
            return CommonResult.success("修改成功");
        }else{
            return CommonResult.success("修改失败");
        }

    }

    /**
     * 获取用户的 关注用户列表
     * @param id 用户id
     * @return 关注用户列表
     */
    @GetMapping("/follow/list")
    public CommonResult getUserFollowList(@RequestParam("id") Long id){
        List<UserInfoVo> list = userService.getUserFollowList(id);

        return CommonResult.success(list);
    }

    /**
     * 获取用户的 粉丝列表
     * @param id 用户ided
     * @return 粉丝列表
     */
    @GetMapping("/followed/list")
    public CommonResult getUserFollowedList(@RequestParam("id") Long id){
        List<UserInfoVo> list = userService.getUserFollowedList(id);

        return CommonResult.success(list);
    }
}
