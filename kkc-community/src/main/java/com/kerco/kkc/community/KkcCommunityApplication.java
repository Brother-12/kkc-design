package com.kerco.kkc.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@ComponentScan("com.kerco.kkc")
@MapperScan("com.kerco.kkc.community.mapper")
@SpringBootApplication
public class KkcCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkcCommunityApplication.class, args);
    }

}
