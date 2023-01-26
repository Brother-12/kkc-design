package com.kerco.kkc.community.feign;

import com.kerco.kkc.common.entity.UserKeyTo;
import com.kerco.kkc.common.entity.UserTo;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.entity.to.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@FeignClient("kkc-member")
public interface MemberFeign {

    @GetMapping("/member/user/in/getUser")
    CommonResult<UserTo> getUserByIdToWrite(@RequestParam("id") Long id);

    @PostMapping("/member/user/getUserList")
    CommonResult<Map<Long, UserKeyTo>> getUserListByIds(@NotEmpty(message = "用户id不能为空") @RequestBody List<Long> list);
}
