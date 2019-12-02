package com.treefinance.payment.batch.service;

import com.treefinance.payment.batch.contsants.JobConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/11/27 下午5:51
 * @Version 1.0
 */
@Component public class ScheduledJobLauncher {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledJobLauncher.class);
    @Autowired private JobLauncher jobLauncher;
    @Autowired private ApplicationContext applicationContext;

    @Scheduled(cron = "0 15 9,19 * * ?") public void launchPremiumScheduleJob()
        throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
        JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> map = new HashMap<>(2);
        JobParameter jobParameter1 = new JobParameter(new Date(), true);

        map.put("target", jobParameter1);
        JobExecution jobExecution = jobLauncher
            .run((Job) applicationContext.getBean(JobConstants.PREMIUM_SCHEDULE_JOB),
                new JobParameters(map));
        logger.info(
            "[launchPremiumScheduleJob finished] job execution exit code:{},exit description:{}",
            jobExecution.getExitStatus().getExitCode(),
            jobExecution.getExitStatus().getExitDescription());
    }
}
