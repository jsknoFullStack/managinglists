package com.jskno.managinglistsbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
@EnableJpaRepositories(basePackages = "com.jskno.managinglistsbe.repositories")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class H2JpaConfig {
    // ...
}
