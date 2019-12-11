package com.treefinance.payment.batch.job.listener;

import com.treefinance.payment.batch.service.monitor.RepositoryMemoryMonitor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxp
 * @date 2019/12/11 上午10:09
 * @Version 1.0
 */
@Configuration public class PaymentJobExecutionListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(PaymentJobExecutionListener.class);
    @Autowired private RepositoryMemoryMonitor repositoryMemoryMonitor;

    @Override public void beforeJob(JobExecution jobExecution) {
        String descriptor = jobExecution.getJobInstance().getJobName() + "_" + jobExecution.getId();
        String appender = StringUtils.join(repositoryMemoryMonitor.summarize(descriptor).entrySet().iterator(),"\n");
        logger.info("[beforeJob] {}", StringUtils.abbreviate(appender,appender.length(),256));
    }

    @Override public void afterJob(JobExecution jobExecution) {
        String descriptor = jobExecution.getJobInstance().getJobName() + "_" + jobExecution.getId();
        String appender = StringUtils.join(repositoryMemoryMonitor.summarize(descriptor).entrySet().iterator(),"\n");
        logger.info("[afterJob] {}", StringUtils.abbreviate(appender,appender.length(),256));
    }
}
