package com.treefinance.payment.batch.config;

import com.github.diamond.client.extend.annotation.DAttribute;
import com.github.diamond.client.extend.annotation.DResource;
import org.springframework.stereotype.Component;

/**
 * @author lxp
 * @date 2019/12/17 下午2:21
 * @Version 1.0
 */
@Component
@DResource
public class DefaultConfig {
    @DAttribute(key = "batch.scheduler.executable") private Boolean overallExecutable;
    public Boolean getOverallExecutable() {
        return overallExecutable;
    }

    public void setOverallExecutable(Boolean overallExecutable) {
        this.overallExecutable = overallExecutable;
    }
}
