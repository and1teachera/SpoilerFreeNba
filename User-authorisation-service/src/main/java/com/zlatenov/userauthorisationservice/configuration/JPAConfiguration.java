package com.zlatenov.userauthorisationservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Angel Zlatenov
 */

@Configuration
@EnableJpaRepositories("com.zlatenov.userauthorisationservice.repository")
public class JPAConfiguration {


}