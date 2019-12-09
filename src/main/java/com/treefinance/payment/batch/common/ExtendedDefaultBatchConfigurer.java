package com.treefinance.payment.batch.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lxp
 * @date 2019/10/24 下午2:05
 * @Version 1.0
 */
@Configuration
@EnableBatchProcessing
public class ExtendedDefaultBatchConfigurer extends DefaultBatchConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ExtendedDefaultBatchConfigurer.class);
//    @Autowired
//    @Qualifier("dataSource_loandb")
//    private DataSource dataSource;
//    @Autowired
//    @Qualifier("transactionManager_loandb")
//    private DataSourceTransactionManager transactionManager;
//    private MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
//
//    @Override
//    protected JobRepository createJobRepository() throws Exception {
//        //in-memory repository
//        factoryBean.setTransactionManager(getTransactionManager());
//        factoryBean.afterPropertiesSet();
//        return factoryBean.getObject();
////        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
////        factory.setDataSource(dataSource);
////        factory.setTransactionManager(transactionManager);
////        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
////        factory.setTablePrefix("BATCH_");
////        factory.setMaxVarCharLength(1000);
////        return factory.getObject();
//    }
//    @Override
//    protected JobLauncher createJobLauncher() throws Exception {
//        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//        jobLauncher.setJobRepository(getJobRepository());
//        jobLauncher.afterPropertiesSet();
//        return jobLauncher;
//    }
//
//    @Override
//    protected JobExplorer createJobExplorer() throws Exception {
//        MapJobExplorerFactoryBean jobExplorerFactoryBean = new MapJobExplorerFactoryBean();
//        jobExplorerFactoryBean.setRepositoryFactory(factoryBean);
//        jobExplorerFactoryBean.afterPropertiesSet();
//        return jobExplorerFactoryBean.getObject();
//    }

    /**
     * stop dataSource from autowired
     * @param dataSource
     */
    @Override
    public void setDataSource(DataSource dataSource) {

    }
}
