package com.treefinance.payment.batch.config;

import com.github.diamond.client.extend.annotation.AfterUpdate;
import com.github.diamond.client.extend.annotation.BeforeUpdate;
import com.github.diamond.client.extend.annotation.DAttribute;
import com.github.diamond.client.extend.annotation.DResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/12/12 下午3:29
 * @Version 1.0
 */
@Component
@DResource
public class SchedulerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    private Map<String,String> map;
    @BeforeUpdate
    public void before(String key, Object newValue) {
        logger.info(key + " update to " + newValue + " start...");
    }
    @AfterUpdate
    public void after(String key, Object newValue) {
        initJobCronMap();
        logger.info(key + " update to " + newValue + " end...");
    }

    @DAttribute(key = "batch.scheduler.executable")
    private Boolean overallExecutable;

    @DAttribute(key = "batch.scheduler.premiumScheduleJob.cron")
    private String premiumScheduleJobCron;
    @DAttribute(key = "batch.scheduler.job.cron")
    private String jobCronStr;

    public Boolean getOverallExecutable() {
        return overallExecutable;
    }

    public void setOverallExecutable(Boolean overallExecutable) {
        this.overallExecutable = overallExecutable;
    }

    public String getPremiumScheduleJobCron() {
        return premiumScheduleJobCron;
    }

    public void setPremiumScheduleJobCron(String premiumScheduleJobCron) {
        this.premiumScheduleJobCron = premiumScheduleJobCron;
    }

    public String getJobCronStr() {
        return jobCronStr;
    }

    public void setJobCronStr(String jobCronStr) {
        this.jobCronStr = jobCronStr;
    }


    private Map<String,String> initJobCronMap() {
        if(map != null) {
            map.clear();
        }
        map = new HashMap<>();
        String[] strs = jobCronStr.split(",");
        for(String str:strs) {
            String[] strs2 = str.split(":");
            map.put(strs2[0],strs2[1]);
        }
        return map;
    }
    public String getJobCron(String jobName) {
        if(map == null) {
            initJobCronMap();
        }
        return map.get(jobName);
    }
}
