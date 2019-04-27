package com.jskno.managinglistsbe.config;

import com.p6spy.engine.spy.P6DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EntityScan("com.jskno.managinglistsbe.domain")
@EnableJpaRepositories(basePackages = {"com.jskno.managinglistsbe.repositories", "com.jskno.managinglistsbe.security.repository"})
@EnableTransactionManagement
@PropertySource("classpath:test.properties")
public class P6SpyTestConfig {

    @Value("${hibernate.hbm2ddl.auto}") String hbm2ddlType;
    @Value("${hibernate.dialect}") String dialect;

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");

        return new P6DataSource(dataSource);
    }

    // configure entityManagerFactory
    // configure transactionManager
    // configure additional Hibernate properties

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();

        result.setDataSource(dataSource());
        result.setPackagesToScan("com.jskno.managinglistsbe.domain", "com.jskno.managinglistsbe.security.persistence");
        result.setJpaVendorAdapter(jpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlType);
        jpaProperties.put("hibernate.dialect", dialect);
        result.setJpaPropertyMap(jpaProperties);

        return result;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject());
        return transactionManager;
    }

    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
}
