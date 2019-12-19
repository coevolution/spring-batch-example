package com.treefinance.payment.batch.listener;

import com.treefinance.payment.batch.service.NoticeServieImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lxp
 * @date 2019/12/11 上午10:09
 * @Version 1.0
 */
@Service public class PaymentJobExecutionListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(PaymentJobExecutionListener.class);
    @Autowired private NoticeServieImpl noticeServie;

    @Override public void beforeJob(JobExecution jobExecution) {

    }

    @Override public void afterJob(JobExecution jobExecution) {
        if (!jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
            noticeServie
                .sendBusinessNotice("batch任务异常", jobExecution.getExitStatus(), this.getClass(),
                    "任务未完成", null);
        }
    }

}
