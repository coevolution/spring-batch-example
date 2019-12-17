package com.treefinance.payment.batch.common;

import com.treefinance.payment.batch.config.SchedulerConfig;
import com.treefinance.payment.batch.service.JobOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    private TaskScheduler taskScheduler;
    private Map<String,ScheduledFuture> futureMap = new ConcurrentHashMap<>();

    @Override public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
        taskScheduler = scheduledTaskRegistrar.getScheduler();
        start();
    }

    @Bean public Executor taskExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();
        logger.info("调度器核心线程数为:{}",cores);
        return new ScheduledThreadPoolExecutor(cores*2);
    }


    @Override public void start() {
        jobOperationService.getJobNames().forEach(each -> {
            String cron = schedulerConfig.getJobCron(each);
            if(!"-".equals(cron)) {
                futureMap.put(each, taskScheduler.schedule(() -> {
                    jobOperationService.runJob(each, new HashMap<String, JobParameter>() {
                        {
                            put("target", new JobParameter(new Date(), true));
                        }
                    });
                }, new CronTrigger(cron)));
            }
        });
    }

    @Override
    public void start(String jobName) {
        String cron = schedulerConfig.getJobCron(jobName);
        if(!"-".equals(cron)) {
            futureMap.put(jobName, taskScheduler.schedule(() -> {
                jobOperationService.runJob(jobName, new HashMap<String, JobParameter>() {
                    {
                        put("target", new JobParameter(new Date(), true));
                    }
                });
            }, new CronTrigger(cron)));
        }
    }

    @Override public void stop(String jobName) {
        futureMap.get(jobName).cancel(false);
    }

    @Override public void stop() {
        synchronized (futureMap) {
            futureMap.entrySet().parallelStream().forEach(each-> {
                each.getValue().cancel(false);
            });
        }
    }
}
