package com.treefinance.payment.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
public class JobLauncherController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(JobLauncherController.class);
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JobOperator jobOperator;


    @RequestMapping("/run/{job}")
    public Object handleJob(@PathVariable String job, HttpServletRequest request) throws Exception {
        Map<String, JobParameter> jobParams = new HashMap<>();
        getAllRequestParam(request).forEach((key,value) -> {
            jobParams.put(key,new JobParameter(value,true));
        });
        if(StringUtils.isEmpty(jobParams.get("target"))) {
            jobParams.put("target",new JobParameter(new Date(),true));

        }
        JobExecution jobExecution = jobLauncher.run((Job) applicationContext.getBean(job), new JobParameters(jobParams));
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
