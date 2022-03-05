package com.freshproduceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//Enables Spring's scheduled task execution Unless @EnableScheduling is used the method will not be scheduled to run.
@EnableScheduling
@SpringBootApplication
public class FreshProduceInventoryReplenisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshProduceInventoryReplenisherApplication.class, args);
    }

}
