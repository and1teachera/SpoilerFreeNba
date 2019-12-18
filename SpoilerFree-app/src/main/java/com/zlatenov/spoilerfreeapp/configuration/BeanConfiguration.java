package com.zlatenov.spoilerfreeapp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Angel Zlatenov
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
