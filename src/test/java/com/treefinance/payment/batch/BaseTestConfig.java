package com.treefinance.payment.batch;

import com.treefinance.payment.batch.test.BigIntegerTest;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * @author lxp
 * @date 2019/11/22 下午3:54
 * @Version 1.0
 */
@ImportResource("classpath:data-source-context.xml")
public class BaseTestConfig {
    protected DataSource dataSource;

    private ConfigurableApplicationContext context;


    @Before
    public void setUp() {
        this.context = new AnnotationConfigApplicationContext(
            BigIntegerTest.TestDataSourceConfiguration.class);
        this.dataSource = (DataSource) context.getBean("dataSource");
    }

    @After
    public void tearDown() {
        if(this.context != null) {
            this.context.close();
        }
    }
    @Configuration
    public static class TestDataSourceConfiguration {

        private static final String CREATE_SQL = "CREATE TABLE FOO  (\n" +
            "\tID BIGINT IDENTITY NOT NULL PRIMARY KEY ,\n" +
            "\tFIRST BIGINT ,\n" +
            "\tSECOND VARCHAR(5) NOT NULL,\n" +
            "\tTHIRD VARCHAR(5) NOT NULL) ;";
        private static final String INSERT_SQL =
            "INSERT INTO FOO (FIRST, SECOND, THIRD) VALUES (1, '2', '3');" +
                "INSERT INTO FOO (FIRST, SECOND, THIRD) VALUES (4, '5', '6');" +
                "INSERT INTO FOO (FIRST, SECOND, THIRD) VALUES (7, '8', '9');" +
                "INSERT INTO FOO (FIRST, SECOND, THIRD) VALUES (10, '11', '12');" +
                "INSERT INTO FOO (FIRST, SECOND, THIRD) VALUES (13, '14', '15');";

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseFactory().getDatabase();
        }

        @Bean
        public DataSourceInitializer initializer(DataSource dataSource) {
            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(dataSource);

            Resource create = new ByteArrayResource(CREATE_SQL.getBytes());
            Resource insert = new ByteArrayResource(INSERT_SQL.getBytes());
            dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create,insert));

            return dataSourceInitializer;
        }
    }

    public static class Foo {
        private int id;
        private int first;
        private String second;
        private String third;

        public Foo(int id, int first, String second, String third) {
            this.id = id;
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }
    }
}
