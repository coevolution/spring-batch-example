package com.treefinance.payment.batch.processing.premium;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lxp
 * @date 2019/10/24 下午4:09
 * @Version 1.0
 */
public class BatchDetailDTO implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 包号
     */
    private String packageNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 业务数据
     */
    private String bizData;

    /**
     * biz_value类型
     */
    private Integer bizType;

    /**
     * userId和scheduleId
     */
    private Integer bizValue;

    /**
     * 同步执行状态FOrderStatusEnum枚举相同
     */
    private Integer syncStatus;

    /**
     * 异步执行状态FOrderStatusEnum枚举相同
     */
    private Integer asyncStatus;

    /**
     * 异常信息
     */
    private String exceptionLog;

    /**
     * 返回数据
     */
    private String rawData;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 失效标志
     */
    private Boolean isDeprecated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public Integer getBizValue() {
        return bizValue;
    }

    public void setBizValue(Integer bizValue) {
        this.bizValue = bizValue;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Integer getAsyncStatus() {
        return asyncStatus;
    }

    public void setAsyncStatus(Integer asyncStatus) {
        this.asyncStatus = asyncStatus;
    }

    public String getExceptionLog() {
        return exceptionLog;
    }

    public void setExceptionLog(String exceptionLog) {
        this.exceptionLog = exceptionLog;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getDeprecated() {
        return isDeprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        isDeprecated = deprecated;
    }
}
