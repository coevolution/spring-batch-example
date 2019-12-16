package com.treefinance.payment.batch.common;

import com.github.diamond.client.extend.annotation.DAttribute;
import com.treefinance.payment.batch.config.SchedulerConfig;
import com.treefinance.payment.batch.service.JobOperationService;
import com.treefinance.payment.batch.service.JobScheduler;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author lxp
 * @date 2019/12/13 上午10:14
 * @Version 1.0
 */
@Configuration public class PaymentSchedulingConfigurer implements SchedulingConfigurer {
    @Autowired private JobOperationService jobOperationService;
    @Autowired private SchedulerConfig schedulerConfig;
    @Autowired private JobScheduler jobScheduler;

    @Override public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
//        jobOperationService.getJobNames().forEach(each -> {
//            scheduledTaskRegistrar.addCronTask(() -> {
//                jobOperationService.runJob(each, new HashMap(){
//                    {
//                        put("target",new JobParameter(new Date(), true));
//                    }
//                });
//            }, schedulerConfig.getPremiumScheduleJobCron());
//        });
//                scheduledTaskRegistrar.addCronTask(() -> jobScheduler.launchPremiumScheduleJob(), schedulerConfig.getPremiumScheduleJobCron());
        Method[] methods = jobScheduler.getClass().getDeclaredMethods();
        Arrays.stream(methods).forEach(each->{
            scheduledTaskRegistrar.addCronTask(() -> {
                each.setAccessible(true);
                try {
                    each.invoke(jobScheduler);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            },schedulerConfig.getPremiumScheduleJobCron());
        });
    }

    @Bean public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(100);
    }

}
