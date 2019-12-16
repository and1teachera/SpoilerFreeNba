package com.zlatenov.teamsinformationservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.zlatenov.teamsinformationservice.repository")
public class JPAConfig {


}