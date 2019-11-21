package com.treefinance.payment.batch.builders;

import com.datatrees.commons.utils.DateUtil;
import com.datatrees.commons.utils.JsonUtil;
import com.treefinance.payment.batch.processing.premium.SinglePremiumScheduleDTO;
import com.treefinance.payment.batch.tasklet.MyTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/11/15 下午4:19
 * @Version 1.0
 */
@Configuration public class SimpleJobConfiguration {
    //    private static SimpleJobBuilder simpleJobBuilder = new SimpleJobBuilder();
    @Autowired
    private MyTasklet tasklet;
    private static String SQL_UPDATE = "insert into t_tp_trade_batch(batch_no, source, biz_type, biz_value, userId, raw_data) values(?, ?, ?, ?, ?, ?)";
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Bean public Job footballJob() throws Exception {
        return this.jobBuilderFactory.get("footballJob").start(step1()).next(step2()).build();
    }

    @Bean public Step step1() throws Exception {
        return this.stepBuilderFactory.get("step1").<SinglePremiumScheduleDTO,SinglePremiumScheduleDTO>chunk(1).reader(reader(new Date())).writer(writer())
            .build();
    }

    @Bean public Step step2() {
        return stepBuilderFactory.get("step2").tasklet(tasklet).build();
    }

    public JdbcPagingItemReader<SinglePremiumScheduleDTO> reader(Date target) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("userDueAtStart", DateUtil.dayStart(target));
        params.put("userDueAtEnd", DateUtil.dayEnd(target));

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause("select id,globalPlatformId,loanOrderId,userId,amount,userDueAt,cleared,status");
        queryProvider.setFromClause("from loandb.t_tp_trade_batch");
        queryProvider.setWhereClause("where userDueAt between :userDueAtStart and :userDueAtEnd and cleared = false");
        queryProvider.setSortKey("id");

        return new JdbcPagingItemReaderBuilder<SinglePremiumScheduleDTO>()
            .name("premium schedule reader").queryProvider(queryProvider.getObject()).parameterValues(params)
            .rowMapper(new PremiumScheduleMapper()).pageSize(100).build();

    }

    @Bean public JdbcBatchItemWriter<SinglePremiumScheduleDTO> writer() {
        String batchNo = DateUtil
            .formatDate(new Date(), "yyyyMMddHHmmss");
        return new JdbcBatchItemWriterBuilder<SinglePremiumScheduleDTO>().sql(SQL_UPDATE).itemPreparedStatementSetter((item,preparedStatement)->{
            preparedStatement.setString(1,batchNo);
            preparedStatement.setString(2,"GARONA");
            preparedStatement.setString(3, "PREMIUM_SCHEDULE_ID");
            preparedStatement.setString(4, String.valueOf(item.getId()));
            preparedStatement.setInt(5, item.getUserId());
            preparedStatement.setString(6, JsonUtil.jsonFromObject(item));
        }).build();
    }

    public class PremiumScheduleMapper implements RowMapper<SinglePremiumScheduleDTO> {

        @Override public SinglePremiumScheduleDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            SinglePremiumScheduleDTO singlePremiumScheduleDTO = new SinglePremiumScheduleDTO();
            singlePremiumScheduleDTO.setId(resultSet.getInt("id"));
            singlePremiumScheduleDTO.setGlobalPlatformId(resultSet.getInt("globalPlatformId"));
            singlePremiumScheduleDTO.setLoanOrderId(resultSet.getInt("loanOrderId"));
            singlePremiumScheduleDTO.setUserId(resultSet.getInt("userId"));
            singlePremiumScheduleDTO.setAmount(resultSet.getBigDecimal("amount"));
            singlePremiumScheduleDTO.setUserDueAt(resultSet.getDate("userDueAt"));
            singlePremiumScheduleDTO.setCleared(resultSet.getBoolean("cleared"));
            singlePremiumScheduleDTO.setStatus(resultSet.getByte("status"));
            return singlePremiumScheduleDTO;
        }
    }
}
