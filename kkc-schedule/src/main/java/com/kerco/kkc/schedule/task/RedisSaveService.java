package com.kerco.kkc.schedule.task;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.schedule.feign.CommunityFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RedisSaveService {

    @Autowired
    private CommunityFeign communityFeign;

    @Scheduled(cron = "*/5 * * * * *")
    @Async
    public void test(){
        CommonResult commonResult = communityFeign.fixedTimeUpdateThumbsUp();
        if(commonResult.getCode() == 200){
            System.out.println("文章点赞 更新成功");
        }
    }
}
