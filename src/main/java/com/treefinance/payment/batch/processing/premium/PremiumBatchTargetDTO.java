package com.treefinance.payment.batch.processing.premium;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lxp
 * @date 2019/10/25 下午3:39
 * @Version 1.0
 */
public class PremiumBatchTargetDTO {
    /**
     * 自增Id
     */
    private Integer id;

    /**
     * 贷款订单ID
     */
    private Integer loanOrderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 资金平台Id
     */
    private Integer globalPlatformId;

    /**
     * 应还总额
     */
    private BigDecimal amount;

    /**
     * 用户应还款日期:放款日当天
     */
    private Date userDueAt;

    /**
     * 计划是否还清
     */
    private Boolean cleared;

    /**
     * 扣款状态。1.正常；0.状态未知
     */
    private Byte deductStatus;
}
