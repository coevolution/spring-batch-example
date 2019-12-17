package com.treefinance.payment.batch.config;

import com.github.diamond.client.extend.annotation.AfterUpdate;
import com.github.diamond.client.extend.annotation.BeforeUpdate;
import com.github.diamond.client.extend.annotation.DAttribute;
import com.github.diamond.client.extend.annotation.DResource;
import com.treefinance.payment.batch.common.ApplicationContextProvider;
import com.treefinance.payment.batch.common.SchedulerControlInterface;
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
@Component @DResource public class SchedulerConfig extends AbstractSchedulerConfig {

    @Override @BeforeUpdate public void before(String key, Object newValue) {
        super.before(key, newValue);
    }

    @Override @BeforeUpdate public void after(String key, Object newValue) {
        super.after(key, newValue);
    }



    @DAttribute(key = "batch.scheduler.premiumScheduleJob.cron") private String
        premiumScheduleJobCron;
    @DAttribute(key = "batch.scheduler.job.cron") private String jobCronStr;

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

    @Override public Map<String, String> initJobCronMap() {
        if (map != null) {
            map.clear();
        }
        map = new HashMap<>(16);
        String[] strs = jobCronStr.split(",");
        for (String str : strs) {
            String[] strs2 = str.split(":");
            map.put(strs2[0], strs2[1]);
        }
        return map;
    }
}
