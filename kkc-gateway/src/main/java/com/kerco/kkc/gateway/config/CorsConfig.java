package com.kerco.kkc.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig{
        @Bean
        public CorsWebFilter corsWebFilter(){
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

            CorsConfiguration corsConfiguration = new CorsConfiguration();
            //1.配置跨域
            //允许哪种请求头跨域
            corsConfiguration.addAllowedHeader("*");
            //允许哪种方法类型跨域 get post delete put
            corsConfiguration.addAllowedMethod("*");
            // 允许哪些请求源跨域 可以配置多个路径
            corsConfiguration.addAllowedOrigin("http://localhost:9528");
            corsConfiguration.addAllowedOrigin("http://localhost:8080");
            // 是否携带cookie跨域
            corsConfiguration.setAllowCredentials(true);
            //允许跨域的路径
            source.registerCorsConfiguration("/**",corsConfiguration);
            return new CorsWebFilter(source);
        }
}
