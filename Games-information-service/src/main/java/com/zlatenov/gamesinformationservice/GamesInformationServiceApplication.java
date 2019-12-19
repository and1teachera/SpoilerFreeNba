package com.zlatenov.gamesinformationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GamesInformationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamesInformationServiceApplication.class, args);
    }

}
