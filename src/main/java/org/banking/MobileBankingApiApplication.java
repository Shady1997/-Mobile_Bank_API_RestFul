/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
 */
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