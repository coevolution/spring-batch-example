package com.treefinance.payment.batch.common;

import com.treefinance.payment.batch.config.SchedulerConfig;
import com.treefinance.payment.batch.service.JobOperationService;
import com.treefinance.payment.batch.service.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author lxp
 * @date 2019/12/13 上午10:14
 * @Version 1.0
 */
@Configuration public class PaymentSchedulingConfigurer
    implements SchedulingConfigurer, SchedulerControlInterface {
    private static final Logger logger = LoggerFactory.getLogger(PaymentSchedulingConfigurer.class);
    @Autowired private JobOperationService jobOperationService;
    @Autowired private SchedulerConfig schedulerConfig;
    @Autowired private JobScheduler jobScheduler;
    private TaskScheduler taskScheduler;
    private ScheduledFuture future;

    @Override public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
        taskScheduler = scheduledTaskRegistrar.getScheduler();
        start();
    }

    @Bean public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(100);
    }


    @Override public void start() {
        future = taskScheduler.schedule(() -> {
            jobScheduler.launchPremiumScheduleJob();
        }, new CronTrigger(schedulerConfig.getPremiumScheduleJobCron()));
    }

    @Override public void stop() {
        future.cancel(false);
    }
}
