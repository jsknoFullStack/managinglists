package com.jskno.managinglistsbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ManagingListsBeConfig {

//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        return new MethodValidationPostProcessor();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
