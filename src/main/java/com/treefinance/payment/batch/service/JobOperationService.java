package com.treefinance.payment.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author lxp
 * @date 2019/12/6 上午10:20
 * @Version 1.0
 */
@Component public class JobOperationService {
    private static final Logger logger = LoggerFactory.getLogger(JobOperationService.class);
    @Autowired private JobOperator jobOperator;
    @Autowired private JobLauncher jobLauncher;
    @Autowired private ApplicationContext applicationContext;

    public Set<String> getJobNames() {
        return jobOperator.getJobNames();
    }

    /**
     * get execution summary with execution id
     * @param executionId
     * @return
     */
    public String getJobExecutionSummary(long executionId) {
        String result = null;
        try {
            result = jobOperator.getSummary(executionId);
        } catch (Exception e) {
            logger.warn("[getJobExecutionSummary] ", e);
            return null;
        }
        return result;
    }

    /**
     * get execution params with execution id
     * @param executionId
     * @return
     */
    public String getJobExecutionParams(long executionId) {
        String result = null;
        try {
            result = jobOperator.getParameters(executionId);
        } catch (NoSuchJobExecutionException e) {
            logger.warn("[getJobExecutionParams] no job execution of id ", executionId);
            return null;
        }
        return result;
    }

    /**
     * fatal in date type
     *
     * @param jobName
     * @param jobParameters
     * @return
     */
    @Deprecated public Long startJob(String jobName, String jobParameters) {
        Long result = null;
        try {
            result = jobOperator.start(jobName, jobParameters);
        } catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException e) {
            e.printStackTrace();
            logger.warn("[startJob] ", e);
            return null;
        }
        logger.info("[startJob] finished.jobName={},jobParameters={}", jobName, jobParameters);
        return result;
    }

    /**
     * restart a job with execution id.
     * can be used when execution failed.
     * @param executionId
     * @return
     */
    public Long restartJob(long executionId) {
        Long result = null;
        try {
            result = jobOperator.restart(executionId);
        } catch (JobInstanceAlreadyCompleteException | NoSuchJobExecutionException | NoSuchJobException | JobParametersInvalidException | JobRestartException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * run a job repeatably.
     * can be used when execution failed.
     * @param jobName
     * @param jobParameterMap
     * @return
     */
    public Long runJob(String jobName, Map<String, JobParameter> jobParameterMap) {
        if (StringUtils.isEmpty(jobParameterMap.get("target"))) {
            jobParameterMap.put("target", new JobParameter(new Date(), true));

        }
        JobExecution jobExecution = null;
        try {
            jobExecution =
                jobLauncher.run((Job) applicationContext.getBean(jobName), new JobParameters(jobParameterMap));
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
            return null;
        }
        logger.info("[runJob] finished.jobName={},jobParameters={}", jobName, jobParameterMap.toString());
        return jobExecution.getId();
    }

    /**
     * stop a job with execution id
     * @param executionId
     * @return
     */
    public Boolean stopJob(long executionId) {
        try {
            return jobOperator.stop(executionId);
        } catch (NoSuchJobExecutionException | JobExecutionNotRunningException e) {
            logger.warn("[stopJob] failed.executionId={}.", executionId,e);
            return false;
        }
    }

    /**
     * stop a job with job name
     * @param jobName
     * @return
     */
    public Boolean stopJob(String jobName) {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions(jobName);
            //只会停第一个?
            Long l = executions.iterator().next();
            boolean result = jobOperator.stop(l);
            logger.info("[stopJob] stop job {} of job execution id:{}", result, l);
            return result;
        } catch (NoSuchJobException | NoSuchJobExecutionException | JobExecutionNotRunningException e) {
            logger.warn("[stopJob] failed.", e);
            return false;
        }
    }
}
