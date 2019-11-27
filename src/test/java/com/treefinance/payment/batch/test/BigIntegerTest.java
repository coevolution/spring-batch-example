package com.treefinance.payment.batch.test;

import com.treefinance.payment.batch.BaseTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author lxp
 * @date 2019/11/18 下午7:59
 * @Version 1.0
 */
public class BigIntegerTest extends BaseTestConfig {
    public static void main(String[] args) {
        BigInteger mapped = new BigInteger("0");
        BigInteger r1 = mapped.setBit(0);
        System.out.println("setBit(0) = "+r1.intValue());
        BigInteger r2 = mapped.setBit(1);
        System.out.println("setBit(1) = "+r2.intValue());
    }
    @Test
    public void test1() {
        try {
            new JdbcBatchItemWriterBuilder<Map<String, Object>>()
                .dataSource(this.dataSource)
                .sql("INSERT INTO FOO VALUES (?, ?, ?)")
                .columnMapped()
                .beanMapped()
                .build();
        }
        catch (IllegalStateException ise) {
            assertEquals("Either an item can be mapped via db column or via bean spec, can't be both",
                ise.getMessage());
        }
        catch (Exception e) {
            fail("Incorrect exception was thrown both mapping types are used" +
                e.getMessage());
        }
    }

}
