package com.treefinance.payment.batch.processing.sample;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lxp
 * @date 2019/10/24 下午2:16
 * @Version 1.0
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private final JdbcTemplate jdbcTemplate;

    @Autowired public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("!!! JOB FINISHED! Time to verify the results");
            jdbcTemplate.query("SELECT first_name, last_name FROM people",
                (rs, row) -> new Person(rs.getString(1), rs.getString(2)))
                .forEach(person -> System.out.println("found<" + person + "> in the database."));
        }
    }
}
