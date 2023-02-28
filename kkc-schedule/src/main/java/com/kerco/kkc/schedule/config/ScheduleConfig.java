package com.kerco.kkc.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;

@Configuration
public class ScheduleConfig {
    /**
     * 自定义线程池
     * @return 线程池
     */
    @Bean
    public Executor taskExecutor() {
        //设置核心线程数为5，最大线程数为20，没有执行任务的线程存活时间为10s，
        return new ThreadPoolExecutor(5, 20,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }
}
