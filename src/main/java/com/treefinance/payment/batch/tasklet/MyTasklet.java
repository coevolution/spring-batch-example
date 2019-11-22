package com.treefinance.payment.batch.tasklet;

import com.datatrees.commons.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @author lxp
 * @date 2019/11/18 下午2:04
 * @Version 1.0
 */
public class MyTasklet implements Tasklet {
    private static Logger logger = LoggerFactory.getLogger(MyTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
        throws Exception {
        logger.info("MyTasklet executed:{},{}",JsonUtil.jsonFromObject(stepContribution.getExitStatus()),JsonUtil.jsonFromObject(chunkContext.isComplete()));
        return RepeatStatus.FINISHED;
    }
}
