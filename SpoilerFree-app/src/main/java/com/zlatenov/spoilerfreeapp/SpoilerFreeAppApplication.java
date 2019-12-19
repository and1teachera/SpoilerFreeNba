package com.zlatenov.spoilerfreeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpoilerFreeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpoilerFreeAppApplication.class, args);
    }

}
