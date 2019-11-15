package com.treefinance.payment.batch.jmx;

import org.springframework.context.ApplicationEvent;

/**
 * @author lxp
 * @date 2019/10/25 下午2:06
 * @Version 1.0
 */
@SuppressWarnings("serial")
public class SimpleMessageApplicationEvent extends ApplicationEvent {
    private String message;

    public SimpleMessageApplicationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    /*
     * @see java.util.EventObject#toString()
     */
    @Override
    public String toString() {
        return "message=["+message+"]," + super.toString();
    }
}
