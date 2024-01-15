package com.academy.enroll.configurations.Resources;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }
}