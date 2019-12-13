package com.treefinance.payment.batch.service.monitor;


import com.treefinance.payment.batch.contsants.JobConstants;
import com.treefinance.payment.batch.service.JobOperationService;
import objectexplorer.MemoryMeasurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * monitor memory used for in-memory repository.
 * useless when using database based repository.
 *
 * @author lxp
 * @date 2019/12/9 下午4:05
 * @Version 1.0
 */
@Service public class RepositoryMemoryMonitor {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryMemoryMonitor.class);
    private static volatile LinkedHashMap<String, Long> repositoryByteSize = new LinkedHashMap<>();
    @Autowired private JobExplorer jobExplorer;
    @Autowired private JobRepository jobRepository;
    @Autowired private JobOperationService jobOperationService;
    @Autowired private MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean;

    private long getJobExplorerSize() {
        return MemoryMeasurer.measureBytes(jobExplorer);
    }

    private long getJobRepositorySize() {
        return MemoryMeasurer.measureBytes(jobRepository);
    }

    private Map<String, Long> innerSummarize(String appender) {
        synchronized (repositoryByteSize) {
            repositoryByteSize
                .put(JobConstants.JOB_REPOSITORY + "_" + appender, getJobRepositorySize());
            //        repositoryByteSize.put(JobConstants.JOB_EXPLORER + "_" + appender, getJobExplorerSize());
        }
        return repositoryByteSize;
    }

    public Map<String, Long> summarize() {
        return innerSummarize("summarized");
    }

    public Map<String, Long> summarize(String descriptor) {
        String str = descriptor + "_" + LocalDateTime.now();
        return innerSummarize(str);
    }

    public Boolean clear() {
        //防止新任务执行
        Boolean reset = jobOperationService.setSchedulerExecutable(false);
        try {
            //检查没有正在执行的任务
            boolean canClear = true;
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 20000) {
                canClear = true;
                List<String> jobs = jobExplorer.getJobNames();
                for (String job : jobs) {
                    Set<JobExecution> executions = jobExplorer.findRunningJobExecutions(job);
                    if (executions == null || executions.size() == 0) {
                        continue;
                    }
                    canClear = false;
                    logger
                        .debug("[clear] 存在正在执行的任务,放弃清理!耗时{}ms", System.currentTimeMillis() - start);
                    break;
                }
            }

            //开始清理
            if (canClear) {
                logger.info("[clear] 开始清理!本次清理检查耗时{}ms",System.currentTimeMillis()-start);
                mapJobRepositoryFactoryBean.clear();
                repositoryByteSize.clear();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //reset
            jobOperationService.setSchedulerExecutable(reset);
        }
    }
}
