package com.treefinance.payment.batch.processing.premium;

import java.util.Date;
import java.util.List;

/**
 * @author lxp
 * @date 2019/10/25 下午4:06
 * @Version 1.0
 */
public class PremiumBatchTargetParams {
    private Date userDueAt;
    private List<Integer> excludedGlobalPlatformId;
    private String source;
    private Integer pageSize;

    public Date getUserDueAt() {
        return userDueAt;
    }

    public void setUserDueAt(Date userDueAt) {
        this.userDueAt = userDueAt;
    }

    public List<Integer> getExcludedGlobalPlatformId() {
        return excludedGlobalPlatformId;
    }

    public void setExcludedGlobalPlatformId(List<Integer> excludedGlobalPlatformId) {
        this.excludedGlobalPlatformId = excludedGlobalPlatformId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
