package edu.byui.apj.storefront.tutorial101.accessingdatarest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class AccessingDataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataRestApplication.class, args);
    }

    @Bean
    CommandLineRunner demo() {
        return args -> {
            log.debug("Logger class: {}", log.getClass().getName());
            // Add any demo logic here if needed
        };
    }
}
