package com.kerco.kkc.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.kerco.kkc")
@MapperScan("com.kerco.kkc.community.mapper")
@SpringBootApplication
public class KkcCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkcCommunityApplication.class, args);
    }

}
