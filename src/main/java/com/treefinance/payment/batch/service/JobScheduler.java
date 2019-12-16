package com.treefinance.payment.batch.service;

import com.treefinance.payment.batch.config.SchedulerConfig;
import com.treefinance.payment.batch.contsants.JobConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * can only contain scheduled task function.
 *
 * @author lxp
 * @date 2019/11/27 下午5:51
 * @Version 1.0
 */
@Component public class JobScheduler {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    @Autowired private JobOperationService jobOperationService;
    @Autowired private SchedulerConfig schedulerConfig;

//    public void singletonLaunch(String jobName, Object param)
//        throws JobParametersInvalidException {
//        Map<String, JobParameter> map = new HashMap<>(2);
//        if(param instanceof String) {
//            map.put("param", new JobParameter((String) param, true));
//        } else if(param instanceof Long) {
//            map.put("param", new JobParameter((Long) param, true));
//        } else if(param instanceof Double) {
//            map.put("param", new JobParameter((Double) param, true));
//        } else if(param instanceof Date) {
//            map.put("param", new JobParameter((Date) param, true));
//        } else {
//            throw new JobParametersInvalidException("任务参数不支持该类型:"+param.getClass().getSimpleName());
//        }
//
//        JobExecution jobExecution =
//            jobOperationService.runJob(jobName, map);
//        logger.info("[singletonLaunch] {} finished. {}",jobName, String.valueOf(jobExecution));
//    }

    public void launchPremiumScheduleJob() {
        Map<String, JobParameter> map = new HashMap<>(2);
        JobParameter jobParameter1 = new JobParameter(new Date(), true);

        map.put("target", jobParameter1);
        JobExecution jobExecution =
            jobOperationService.runJob(JobConstants.PREMIUM_SCHEDULE_JOB, map);
        logger.info("[launchPremiumScheduleJob] finished.", String.valueOf(jobExecution));
    }

    public void launchJob1() {
        Map<String, JobParameter> map = new HashMap<>(2);
        JobParameter jobParameter1 = new JobParameter(new Date(), true);

        map.put("job1_param1", jobParameter1);
        JobExecution jobExecution = jobOperationService.runJob(JobConstants.JOB_1, map);
        logger.info("[launchJob1] finished.", String.valueOf(jobExecution));
    }
}
