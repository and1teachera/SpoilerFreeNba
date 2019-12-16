package com.zlatenov.gamesinformationservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.zlatenov.gamesinformationservice.repository")
public class JPAConfig {


}