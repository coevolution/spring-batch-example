package com.treefinance.payment.batch.config;

import com.github.diamond.client.extend.annotation.AfterUpdate;
import com.github.diamond.client.extend.annotation.BeforeUpdate;
import com.treefinance.payment.batch.common.ApplicationContextProvider;
import com.treefinance.payment.batch.common.SchedulerControlInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Inherited;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/12/17 上午10:49
 * @Version 1.0
 */
public abstract class AbstractSchedulerConfig {
    private static final Logger logger = LoggerFactory.getLogger(AbstractSchedulerConfig.class);
    protected Map<String,String> map;
    protected void before(String key, Object newValue) {
        SchedulerControlInterface bean =
            ApplicationContextProvider.getApplicationContext().getBean(SchedulerControlInterface.class);
        bean.stop();
        logger.info(key + " update to " + newValue + " start...");
    }
    protected void after(String key, Object newValue) {
        SchedulerControlInterface bean =
            ApplicationContextProvider.getApplicationContext().getBean(SchedulerControlInterface.class);
        initJobCronMap();
        bean.start();
        logger.info(key + " update to " + newValue + " end...");
    }

    /**
     * 获取各任务cron表达式
     * @return
     */
    abstract Map<String,String> initJobCronMap();

    /**
     * 获取任务cron表达式
     * @param jobName
     * @return
     */
    public String getJobCron(String jobName) {
        if(map == null) {
            initJobCronMap();
        }
        return map.get(jobName);
    }
}
