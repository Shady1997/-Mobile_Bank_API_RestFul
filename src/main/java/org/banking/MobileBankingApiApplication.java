package org.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.banking")
public class MobileBankingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobileBankingApiApplication.class, args);
    }
}