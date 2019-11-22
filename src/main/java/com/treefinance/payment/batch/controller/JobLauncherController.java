package com.treefinance.payment.batch.controller;

import com.datatrees.commons.utils.DateUtil;
import com.datatrees.commons.utils.JsonUtil;
import com.treefinance.payment.batch.builders.Job1Configuration;
import com.treefinance.payment.batch.builders.SimpleJobConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping("/run/{job}/{datetime}")
    public Object handleJob(@PathVariable String job, @PathVariable String datetime) throws Exception {
        JobParameter jobParameter1 = new JobParameter(datetime,true);
        JobParameter jobParameter2 = new JobParameter(DateUtil.parseDate(datetime, "yyyyMMddHHmmss"));
        Map<String, JobParameter> map = new HashMap<>();
        map.put("datetime",jobParameter1);
        map.put("dummyDate",jobParameter2);
        JobExecution jobExecution = jobLauncher.run((Job) applicationContext.getBean(job), new JobParameters(map));
        return jobExecution.getExitStatus();
    }

    @RequestMapping("/stop/{job}")
    public Object stopJob(@PathVariable String job, @PathVariable String datetime) {
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
