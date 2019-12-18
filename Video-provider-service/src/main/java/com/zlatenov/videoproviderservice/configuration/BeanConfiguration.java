package com.zlatenov.videoproviderservice.configuration;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Angel Zlatenov
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
