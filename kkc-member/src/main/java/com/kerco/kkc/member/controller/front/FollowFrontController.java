package com.kerco.kkc.member.controller.front;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.member.entity.Follow;
import com.kerco.kkc.member.entity.vo.FollowVo;
import com.kerco.kkc.member.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/follow")
@RestController
public class FollowFrontController {

    @Autowired
    private FollowService followService;

    /**
     * 检查用户是否被关注
     * @param followVo 关注信息
     * @param request HttpServletRequest
     * @return
     */
    @PostMapping("/check")
    public CommonResult checkUserFollow(@Valid @RequestBody FollowVo followVo, HttpServletRequest request){
        int result = followService.checkUserFollow(followVo,request);
        if(result == 0){
            return CommonResult.success(false);
        }else {
            return CommonResult.success(true);
        }
    }

    @PostMapping("/confirm")
    public CommonResult userConfirmFollow(@Valid @RequestBody FollowVo followVo, HttpServletRequest request){
        int result = followService.userConfirmFollow(followVo, request);

        if(result == 0){
            return CommonResult.success(false);
        }else {
            return CommonResult.success(true);
        }
    }

    @PostMapping("/cancel")
    public CommonResult userCancelFollow(@Valid @RequestBody FollowVo followVo, HttpServletRequest request){
        int result = followService.userCancelFollow(followVo,request);

        if(result == 0){
            return CommonResult.success(false);
        }else {
            return CommonResult.success(true);
        }
    }
}
