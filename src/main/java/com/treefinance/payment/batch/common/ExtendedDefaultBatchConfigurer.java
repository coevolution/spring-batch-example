package com.treefinance.payment.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author lxp
 * @date 2019/10/24 下午2:05
 * @Version 1.0
 */
@Configuration @EnableBatchProcessing public class ExtendedDefaultBatchConfigurer
    extends DefaultBatchConfigurer {
    private static final Logger logger =
        LoggerFactory.getLogger(ExtendedDefaultBatchConfigurer.class);

    private PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean() throws Exception {
        MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
        mapJobRepositoryFactoryBean.setTransactionManager(transactionManager());
        mapJobRepositoryFactoryBean.afterPropertiesSet();
        return mapJobRepositoryFactoryBean;
    }
    @Override protected JobRepository createJobRepository() throws Exception {
        //in-memory repository
        return mapJobRepositoryFactoryBean().getObject();
        //        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        //        factory.setDataSource(dataSource);
        //        factory.setTransactionManager(transactionManager);
        //        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        //        factory.setTablePrefix("BATCH_");
        //        factory.setMaxVarCharLength(1000);
        //        return factory.getObject();
    }

    @Override protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Override protected JobExplorer createJobExplorer() throws Exception {
        MapJobExplorerFactoryBean jobExplorerFactoryBean = new MapJobExplorerFactoryBean();
        jobExplorerFactoryBean.setRepositoryFactory(mapJobRepositoryFactoryBean());
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }

    /**
     * stop dataSource from autowired
     *
     * @param dataSource
     */
//        @Override public void setDataSource(DataSource dataSource) {
//
//        }

}
