package com.treefinance.payment.batch.test;

import com.treefinance.payment.batch.BaseTestConfig;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.context.annotation.ImportResource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author lxp
 * @date 2019/11/22 下午3:53
 * @Version 1.0
 */
public class JdbcPagingItemReaderBuilderTest extends BaseTestConfig {
    @Test
    public void testPageSize1() throws Exception {
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("ID", Order.DESCENDING);
        Date start = DateUtils.parseDate("2019-11-21 00:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = DateUtils.parseDate("2019-11-21 23:59:59","yyyy-MM-dd HH:mm:ss");
        Map<String, Object> params = new HashMap<>();
        params.put("dayStart",start);
        params.put("dayEnd",end);

        JdbcPagingItemReader<Foo> reader = new JdbcPagingItemReaderBuilder<Foo>()
            .dataSource(this.dataSource)
            .pageSize(10)
            .maxItemCount(2)
            .selectClause("SELECT ID, FIRST, SECOND, THIRD")
            .fromClause("FOO")
//            .whereClause("WHERE USER_DUE_AT BETWEEN :dayStart and :dayEnd")
//            .parameterValues(params)
            .sortKeys(sortKeys)
            .saveState(false)
            .rowMapper((rs, rowNum) -> new Foo(rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4)))
            .build();
        reader.afterPropertiesSet();
        ExecutionContext executionContext = new ExecutionContext();
        reader.open(executionContext);
        Foo item1 = reader.read();
        Foo item2 = reader.read();
        reader.update(executionContext);
        reader.close();
        assertEquals(4, item1.getId());
        assertEquals(13, item1.getFirst());
        assertEquals("14", item1.getSecond());
        assertEquals("15", item1.getThird());

        assertEquals(3, item2.getId());
        assertEquals(10, item2.getFirst());
        assertEquals("11", item2.getSecond());
        assertEquals("12", item2.getThird());
    }
}
