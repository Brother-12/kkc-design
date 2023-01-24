package com.kerco.kkc.community.feign;

import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.to.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("kkc-member")
public interface MemberFeign {

    @GetMapping("/member/user/in/getUser")
    CommonResult<UserTo> getUserByIdToWrite(@RequestParam("id") Long id);
}
