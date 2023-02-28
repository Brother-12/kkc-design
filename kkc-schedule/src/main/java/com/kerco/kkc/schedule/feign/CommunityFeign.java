package com.kerco.kkc.schedule.feign;

import com.kerco.kkc.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient("kkc-community")
public interface CommunityFeign {

    /**
     * 定时更新 文章表里面的点赞数
     * @return 执行结果
     */
    @PostMapping("/community/article/fixed/update/thumbsUp")
    CommonResult fixedTimeUpdateThumbsUp();
}
