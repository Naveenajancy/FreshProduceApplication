package com.freshproduceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//This enables detection of @Scheduled annotations on any Spring-managed bean in the container.
//Enables Spring's scheduled task execution capability
@EnableScheduling
@SpringBootApplication
public class FreshProduceInventorySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshProduceInventorySystemApplication.class, args);
    }

}
