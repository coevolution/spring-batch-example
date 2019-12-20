package com.treefinance.payment.batch.config;

import com.github.diamond.client.extend.annotation.DAttribute;
import com.github.diamond.client.extend.annotation.DResource;
import org.springframework.stereotype.Component;

/**
 * @author lxp
 * @date 2019/12/20 上午10:13
 * @Version 1.0
 */
@DResource
@Component(value = "dubboConfig")
public class DubboConfig {
    @DAttribute(key="dubbo.comsumer.app.name")
    private String comsumerAppName;
    @DAttribute(key="dubbo.zkaddress")
    private String registryAddress;
    @DAttribute(key="dubbo.username")
    private String userName;
    private DubboConfig() {

    }

    public String getComsumerAppName() {
        return comsumerAppName;
    }

    public void setComsumerAppName(String comsumerAppName) {
        this.comsumerAppName = comsumerAppName;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
