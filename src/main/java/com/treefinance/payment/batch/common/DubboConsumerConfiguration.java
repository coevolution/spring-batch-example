package com.treefinance.payment.batch.common;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.treefinance.payment.batch.config.DubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxp
 * @date 2019/12/20 上午10:31
 * @Version 1.0
 */
@Configuration
@EnableDubbo(scanBasePackages = {"com.treefintech.notify.facade.service","com.treefinance.payment.batch.service"})
public class DubboConsumerConfiguration {
    @Autowired DubboConfig dubboConfig;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("tp-payment-batch");
        return applicationConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        consumerConfig.setCheck(false);
        consumerConfig.setRetries(0);
        return consumerConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(dubboConfig.getRegistryAddress());
        registryConfig.setClient("curator");
        return registryConfig;
    }
}
