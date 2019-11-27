package com.treefinance.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author dashu
 */
@SpringBootApplication
@ImportResource(value = "classpath:META-INF/spring/spring-context.xml")
@EnableScheduling
public class PaymentBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentBatchApplication.class, args);
    }

}
