package com.kerco.kkc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KkcGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkcGatewayApplication.class, args);
    }

}
