package com.treefinance.payment.batch.common;

import com.treefinance.payment.batch.processing.premium.SinglePremiumScheduleDTO;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;

/**
 * @author lxp
 * @date 2019/10/25 上午11:44
 * @Version 1.0
 * Wraps calls for 'Processing' methods which output a single Object to write
 * the string representation of the object to the log.
 */

public class LogAdvice {
    private static Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    public void doStronglyTypedLogging(JoinPoint joinPoint, Object item){
        logger.info(joinPoint.toShortString()+ " Processed: " + item);
    }

    public void doStronglyTypedLoggingForNoParams(JoinPoint joinPoint,Object returnVal){
        logger.info(joinPoint.toShortString()+ " Processed: "+((SinglePremiumScheduleDTO)returnVal).getId());
    }
}
