package com.treefinance.payment.batch.processing.premium;

import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;

/**
 * @author lxp
 * @date 2019/10/25 上午11:24
 * @Version 1.0
 */
public class PremiumJobLauncher extends SimpleJobLauncher {
    private JobRegistryBeanPostProcessor job;
}
