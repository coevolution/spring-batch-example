package com.treefinance.payment.batch;

import com.treefinance.payment.batch.builders.Job1Configuration;
import com.treefinance.payment.batch.common.SampleBatchConfigurer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lxp
 * @date 2019/11/19 下午4:26
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@Import({SampleBatchConfigurer.class, Job1Configuration.class})
@ImportResource(value = {"classpath:META-INF/spring/spring-context.xml", "classpath:job-runner-context.xml"})
public class Job1BatchTest {
    @Autowired private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private Job job1;

    @Test public void test1() throws Exception {
        System.out.println("test1 start");
        jobLauncherTestUtils.setJob(job1);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
        System.out.println("test1 end");
    }
}
