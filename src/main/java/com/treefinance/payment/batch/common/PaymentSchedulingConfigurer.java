package com.treefinance.payment.batch.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;

/**
 * @author lxp
 * @date 2019/12/13 上午10:14
 * @Version 1.0
 */
@Configuration
public class PaymentSchedulingConfigurer implements SchedulingConfigurer {
    @Override public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }
    @Bean
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(100);
    }
}
