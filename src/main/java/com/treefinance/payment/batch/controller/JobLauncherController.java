package com.treefinance.payment.batch.controller;

import com.datatrees.commons.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lxp
 * @date 2019/11/15 下午5:21
 * @Version 1.0
 */
@RestController
@RequestMapping("launcher")
public class JobLauncherController {
    private static final Logger logger = LoggerFactory.getLogger(JobLauncherController.class);
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JobOperator jobOperator;


    @RequestMapping("/run/{job}")
    public Object handleJob(@PathVariable String job, @RequestParam(required = false) String datetime) throws Exception {
        Map<String, JobParameter> map = new HashMap<>();
        JobParameter jobParameter1 = null;
        if(datetime == null) {
            jobParameter1 = new JobParameter(new Date(),true);
        } else {
            jobParameter1 = new JobParameter(DateUtil.parseDate(datetime,"yyyyMMddHHmmss"), true);
        }

        map.put("target",jobParameter1);
        JobExecution jobExecution = jobLauncher.run((Job) applicationContext.getBean(job), new JobParameters(map));
        return jobExecution.getExitStatus();
    }

    @RequestMapping("/stop/{job}")
    public Object stopJob(@PathVariable String job) {
        Set<Long> executions = null;
        try {
            executions = jobOperator.getRunningExecutions(job);
            //只会停第一个?
            Long l = executions.iterator().next();
            return jobOperator.stop(l)+":"+l;
        } catch (NoSuchJobException | NoSuchJobExecutionException | JobExecutionNotRunningException e) {
            logger.error("Expception:{}",e.getMessage());
            return false;
        }
    }
}
