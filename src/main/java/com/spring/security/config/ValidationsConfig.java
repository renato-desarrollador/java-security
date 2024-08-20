package com.spring.security.config;

import com.spring.security.services.models.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfig {
    //Creamos el Bean
    @Bean
    public UserValidation userValidation(){
        return new UserValidation();
    }
}
