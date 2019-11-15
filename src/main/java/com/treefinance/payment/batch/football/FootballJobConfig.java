package com.treefinance.payment.batch.football;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxp
 * @date 2019/10/28 上午10:23
 * @Version 1.0
 */
@Configuration
public class FootballJobConfig {
//    @Bean
//    public Job footballJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders) {
//        return jobBuilders.get("FootballJob").start()
//    }
//
//    @Bean
//    public Step footballJobStep1(StepBuilderFactory stepBuilders) {
//        return stepBuilders.get("footballJobStep1").<Player,PlayerSummary> chunk(10).reader(reader())
//    }
//
//    @Bean
//    public FlatFileItemReader<Player> reader() {
//        return new FlatFileItemReader<Player>().name()
//    }
}
