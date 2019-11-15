package com.treefinance.payment.batch.common;

/**
 * @author lxp
 * @date 2019/10/25 上午11:44
 * @Version 1.0
 * Wraps calls for 'Processing' methods which output a single Object to write
 * the string representation of the object to the log.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogAdvice {
    private static Log log = LogFactory.getLog(LogAdvice.class);

    public void doStronglyTypedLogging(Object item){
        log.info("Processed: " + item);
    }
}
