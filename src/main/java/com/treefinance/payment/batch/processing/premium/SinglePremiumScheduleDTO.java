package com.treefinance.payment.batch.processing.premium;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lxp
 * @date 2019/10/25 下午3:30
 * @Version 1.0
 */
public class SinglePremiumScheduleDTO implements Serializable {
    private Integer id;
    private Integer globalPlatformId;
    private Integer loanOrderId;
    private Integer userId;
    private BigDecimal amount;
    private Date userDueAt;
    private Boolean cleared;
    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGlobalPlatformId() {
        return globalPlatformId;
    }

    public void setGlobalPlatformId(Integer globalPlatformId) {
        this.globalPlatformId = globalPlatformId;
    }

    public Integer getLoanOrderId() {
        return loanOrderId;
    }

    public void setLoanOrderId(Integer loanOrderId) {
        this.loanOrderId = loanOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getUserDueAt() {
        return userDueAt;
    }

    public void setUserDueAt(Date userDueAt) {
        this.userDueAt = userDueAt;
    }

    public Boolean getCleared() {
        return cleared;
    }

    public void setCleared(Boolean cleared) {
        this.cleared = cleared;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
