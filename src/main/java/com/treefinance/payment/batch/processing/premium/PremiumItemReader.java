package com.treefinance.payment.batch.processing.premium;

import com.datatrees.dao.loandb.domain.SinglePremiumSchedule;
import com.datatrees.dao.loandb.domain.SinglePremiumScheduleCriteria;
import com.datatrees.dao.loandb.mapper.SinglePremiumScheduleMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.AbstractSqlPagingQueryProvider;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/10/25 下午3:27
 * @Version 1.0
 */
@Component
public class PremiumItemReader {
    @Autowired private SinglePremiumScheduleMapper singlePremiumScheduleMapper;

    public List<SinglePremiumSchedule> read(PremiumBatchTargetParams params) {
        SinglePremiumScheduleCriteria criteria = new SinglePremiumScheduleCriteria();
        criteria.createCriteria().andUserDueAtEqualTo(params.getUserDueAt())
            .andClearedEqualTo(false).andDeductStatusEqualTo((byte) 1).andGlobalPlatformIdNotIn(params.getExcludedGlobalPlatformId());

        List<SinglePremiumSchedule> lists =
            singlePremiumScheduleMapper.selectByExample(criteria);
        return lists;
    }
}
