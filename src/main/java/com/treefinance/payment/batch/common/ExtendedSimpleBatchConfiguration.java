package com.treefinance.payment.batch.common;

import org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxp
 * @date 2019/12/6 下午3:54
 * @Version 1.0
 */
@Configuration
public class ExtendedSimpleBatchConfiguration extends SimpleBatchConfiguration {
    /**
     * register all jobs as they are created.
     * @return
     * @throws Exception
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() throws Exception {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry());
        return postProcessor;
    }
}
