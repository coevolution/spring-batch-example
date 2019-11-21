package com.treefinance.payment;

import com.treefinance.payment.batch.common.SampleBatchConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath:META-INF/spring/spring-context.xml")
public class PaymentBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentBatchApplication.class, args);
    }

}
