package com.treefinance.payment.batch.job.configure;

import com.treefinance.payment.batch.listener.PaymentJobExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author lxp
 * @date 2019/11/19 下午3:22
 * @Version 1.0
 */
@Configuration
public class Job1Configuration {
    private Random random;
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private PaymentJobExecutionListener paymentJobExecutionListener;

    public Job1Configuration(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.random = new Random();
    }

    @Bean public Job job1() {
        return jobBuilderFactory.get("job1").start(step1()).next(step2())
            .listener(paymentJobExecutionListener)
            .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1").tasklet((stepContribution, chunkContext) -> {
            System.out.println("hello");
            Thread.sleep(random.nextInt(3000));
            return RepeatStatus.FINISHED;
        }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2").tasklet((stepContribution, chunkContext) -> {
            System.out.println("world");
            int nextInt = random.nextInt(3000);
            Thread.sleep(nextInt);
            if (nextInt % 5 == 0) {
                throw new RuntimeException("Boom!");
            }
            return RepeatStatus.FINISHED;
        }).build();
    }
}
