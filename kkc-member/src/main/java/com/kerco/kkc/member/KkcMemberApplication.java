package com.kerco.kkc.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 我们需要把kkc-common包下的内容也给注入进来
 * @ComponentScan("com.kerco.kkc")
 */
@MapperScan("com.kerco.kkc.member.mapper")
@ComponentScan("com.kerco.kkc")
@SpringBootApplication
public class KkcMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkcMemberApplication.class, args);
    }

}
