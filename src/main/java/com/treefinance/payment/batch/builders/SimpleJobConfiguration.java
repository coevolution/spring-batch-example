package com.treefinance.payment.batch.builders;

import com.datatrees.commons.utils.DateUtil;
import com.datatrees.commons.utils.JsonUtil;
import com.datatrees.dao.loandb.domain.SinglePremiumSchedule;
import com.treefinance.payment.batch.processing.premium.SinglePremiumScheduleDTO;
import com.treefinance.payment.batch.tasklet.MyTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxp
 * @date 2019/11/15 下午4:19
 * @Version 1.0
 */
@Configuration public class SimpleJobConfiguration {
    private static String SQL_UPDATE =
        "insert into t_tp_trade_batch(batch_no, source, biz_type, biz_value, userId, raw_data) values(?, ?, ?, ?, ?, ?)";
    @Resource private JobBuilderFactory jobBuilderFactory;
    @Resource private StepBuilderFactory stepBuilderFactory;

    @Bean public Job premiumScheduleJob(JobRepository jobRepository, Step step1, Step step2)
        throws Exception {
        return this.jobBuilderFactory.get("premiumScheduleJob").repository(jobRepository)
            .start(step1).next(step2).build();
    }

    @Bean public Step step1(PlatformTransactionManager transactionManager,
        ItemReader<SinglePremiumScheduleDTO> reader, ItemWriter<SinglePremiumScheduleDTO> writer)
        throws Exception {
        return this.stepBuilderFactory.get("step1").transactionManager(
            transactionManager).<SinglePremiumScheduleDTO, SinglePremiumScheduleDTO>chunk(2)
            .reader(reader).writer(writer).build();
    }

    @Bean public Step step2() {
        return stepBuilderFactory.get("step2").tasklet(myTasklet()).build();
    }

    private MyTasklet myTasklet() {
        return new MyTasklet();
    }

    @StepScope
    @Bean public JdbcPagingItemReader<SinglePremiumScheduleDTO> reader(DataSource dataSource, @Value("#{jobParameters[datetime]}") String datetime)
        throws Exception {
        Date target = DateUtil.parseDate(datetime,"yyyyMMddHHmmss");
        Map<String, Object> params = new HashMap<>();
        params.put("userDueAtStart", DateUtil.dayStart(target));
        params.put("userDueAtEnd", DateUtil.dayEnd(target));

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause(
            "select id,global_Platform_Id,loan_Order_Id,user_Id,amount,user_Due_At,cleared,status");
        queryProvider.setFromClause("from loandb.qs_single_premium_schedule");
        queryProvider.setWhereClause(
            "where user_Due_At between :userDueAtStart and :userDueAtEnd and cleared = false");
        queryProvider.setSortKey("id");

        return new JdbcPagingItemReaderBuilder<SinglePremiumScheduleDTO>().dataSource(dataSource)
            .name("premium schedule reader").queryProvider(queryProvider.getObject())
            .parameterValues(params).rowMapper(new PremiumScheduleMapper()).pageSize(100).build();

    }

    @StepScope
    @Bean public JdbcBatchItemWriter<SinglePremiumScheduleDTO> writer(DataSource dataSource, @Value("#{jobParameters[datetime]}") String datetime) {
        return new JdbcBatchItemWriterBuilder<SinglePremiumScheduleDTO>().dataSource(dataSource)
            .sql(SQL_UPDATE).itemPreparedStatementSetter((item, preparedStatement) -> {
                preparedStatement.setString(1, datetime);
                preparedStatement.setString(2, "GARONA");
                preparedStatement.setString(3, "PREMIUM_SCHEDULE_ID");
                preparedStatement.setString(4, String.valueOf(item.getId()));
                preparedStatement.setInt(5, item.getUserId());
                preparedStatement.setString(6, JsonUtil.jsonFromObject(item));
            }).build();
    }

    public class PremiumScheduleMapper implements RowMapper<SinglePremiumScheduleDTO> {

        @Override public SinglePremiumScheduleDTO mapRow(ResultSet resultSet, int i)
            throws SQLException {
            SinglePremiumScheduleDTO singlePremiumScheduleDTO = new SinglePremiumScheduleDTO();
            singlePremiumScheduleDTO.setId(resultSet.getInt("id"));
            singlePremiumScheduleDTO.setGlobalPlatformId(resultSet.getInt("global_Platform_Id"));
            singlePremiumScheduleDTO.setLoanOrderId(resultSet.getInt("loan_Order_Id"));
            singlePremiumScheduleDTO.setUserId(resultSet.getInt("user_Id"));
            singlePremiumScheduleDTO.setAmount(resultSet.getBigDecimal("amount"));
            singlePremiumScheduleDTO.setUserDueAt(resultSet.getDate("user_Due_At"));
            singlePremiumScheduleDTO.setCleared(resultSet.getBoolean("cleared"));
            singlePremiumScheduleDTO.setStatus(resultSet.getByte("status"));
            return singlePremiumScheduleDTO;
        }
    }
}
