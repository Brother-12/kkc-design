package com.kerco.kkc.picture;

import com.kerco.kkc.common.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan("com.kerco.kkc")
@SpringBootApplication()
public class KkcPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkcPictureApplication.class, args);
    }

}
