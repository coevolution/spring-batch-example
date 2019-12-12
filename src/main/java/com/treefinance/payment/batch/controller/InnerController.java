package com.treefinance.payment.batch.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.treefinance.payment.batch.service.JobOperationService;
import com.treefinance.payment.batch.service.monitor.RepositoryMemoryMonitor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lxp
 * @date 2019/11/28 上午10:45
 * @Version 1.0
 */
@RestController @RequestMapping("/inner") public class InnerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(InnerController.class);
    @Autowired private JobOperationService jobOperationService;
    @Autowired private RepositoryMemoryMonitor repositoryMemoryMonitor;

    @RequestMapping("/log/{target}")
    public Object setLoggerLever(@PathVariable String target, @RequestParam String level,
        HttpRequest request) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(target).setLevel(Level.valueOf(level));
        String result = String.format("log level配置更新:%s log level set to %s", target,
            loggerContext.getLogger(target).getLevel());
        logger.info(result);
        return result;
    }

    @RequestMapping("operations/restart/{executionId}")
    public Long restartJob(@PathVariable long executionId) {
        return jobOperationService.restartJob(executionId);
    }

    /**
     * start a job
     *
     * @param jobName         example:premiumScheduleJob
     * @param jobParameterStr example:{target(date)=2019/12/05}
     * @return
     */
    @RequestMapping("operations/start") public Long restartJob(@RequestParam String jobName,
        @RequestParam String jobParameterStr) {
        return jobOperationService.startJob(jobName, jobParameterStr);
    }

    /**
     * @param job
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/operations/run/{job}") public Long handleJob(@PathVariable String job,
        HttpServletRequest request) throws Exception {
        Map<String, JobParameter> jobParams = new HashMap<>();
        getAllRequestParam(request).forEach((key, value) -> {
            jobParams.put(key, new JobParameter(value, true));
        });
        return jobOperationService.runJob(job, jobParams);
    }

    @RequestMapping("operations/parameters/{executionId}")
    public String getParameters(@PathVariable long executionId) {
        return jobOperationService.getJobExecutionParams(executionId);
    }

    @RequestMapping("operations/summary/{executionId}")
    public String summary(@PathVariable long executionId) {
        return jobOperationService.getJobExecutionSummary(executionId);
    }

    @RequestMapping("operations/jobs") public Set<String> getJobs() {
        return jobOperationService.getJobNames();
    }

    @RequestMapping("/operation/stop/{job}") public Boolean stopJob(@PathVariable String job) {
        return jobOperationService.stopJob(job);
    }

    @RequestMapping("summaries/memory") public Object monitorInMemoryRepository() {
        return StringUtils.join(repositoryMemoryMonitor.summarize().entrySet().iterator(), "\n");
    }

    @RequestMapping("repository/clear") public Object clear() {
        return repositoryMemoryMonitor.clear();
    }
}
