package com.treefinance.payment.batch.config;

import com.github.diamond.client.extend.annotation.AfterUpdate;
import com.github.diamond.client.extend.annotation.BeforeUpdate;
import com.github.diamond.client.extend.annotation.DAttribute;
import com.github.diamond.client.extend.annotation.DResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author lxp
 * @date 2019/12/12 下午3:29
 * @Version 1.0
 */
@Component
@DResource
public class SchedulerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);
    @BeforeUpdate
    public void before(String key, Object newValue) {
        logger.info(key + " update to " + newValue + " start...");
    }
    @AfterUpdate
    public void after(String key, Object newValue) {
        logger.info(key + " update to " + newValue + " end...");
    }

    @DAttribute(key = "batch.scheduler.executable")
    private Boolean overallExecutable;

    @DAttribute(key = "batch.scheduler.premiumScheduleJob.cron")
    private String premiumScheduleJobCron;

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
}
