package com.academy.enroll.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfigurations {

    @Bean
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }
}
