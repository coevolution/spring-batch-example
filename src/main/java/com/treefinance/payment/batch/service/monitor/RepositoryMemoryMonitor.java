package com.treefinance.payment.batch.service.monitor;


import com.treefinance.payment.batch.contsants.JobConstants;
import objectexplorer.MemoryMeasurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * monitor memory used for in-memory repository.
 * useless when using database based repository.
 *
 * @author lxp
 * @date 2019/12/9 下午4:05
 * @Version 1.0
 */
@Service public class RepositoryMemoryMonitor {
    private static volatile LinkedHashMap<String, Long> repositoryByteSize = new LinkedHashMap<>();
    @Autowired private JobExplorer jobExplorer;
    @Autowired private JobRepository jobRepository;

    private long getJobExplorerSize() {
        return MemoryMeasurer.measureBytes(jobExplorer);
    }

    private long getJobRepositorySize() {
        return MemoryMeasurer.measureBytes(jobRepository);
    }

    private synchronized Map<String, Long> innerSummarize(String appender) {
        repositoryByteSize
            .put(JobConstants.JOB_REPOSITORY + "_" + appender, getJobRepositorySize());
//        repositoryByteSize.put(JobConstants.JOB_EXPLORER + "_" + appender, getJobExplorerSize());
        return repositoryByteSize;
    }

    public Map<String, Long> summarize() {
        return innerSummarize("summarized");
    }

    public Map<String, Long> summarize(String descriptor) {
        String str = descriptor + "_" + LocalDateTime.now();
        return innerSummarize(str);
    }
}
