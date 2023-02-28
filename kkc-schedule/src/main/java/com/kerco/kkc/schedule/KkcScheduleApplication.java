package com.kerco.kkc.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务服务
 * 定时将redis中存储的数据进行保存
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class KkcScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkcScheduleApplication.class, args);
    }
}
