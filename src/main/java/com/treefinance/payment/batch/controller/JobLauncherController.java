package com.treefinance.payment.batch.controller;

import com.datatrees.commons.utils.JsonUtil;
import com.treefinance.payment.batch.builders.Job1Configuration;
import com.treefinance.payment.batch.builders.SimpleJobConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lxp
 * @date 2019/11/15 下午5:21
 * @Version 1.0
 */
@RestController
@RequestMapping("launcher")
@Import({Job1Configuration.class,SimpleJobConfiguration.class})
public class JobLauncherController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping("/run/{job}")
    public Object handleJob1(@PathVariable String job) throws Exception {
        JobExecution jobExecution = jobLauncher.run((Job) applicationContext.getBean(job), new JobParameters());
        return JsonUtil.jsonFromObject(jobExecution);
    }
}
