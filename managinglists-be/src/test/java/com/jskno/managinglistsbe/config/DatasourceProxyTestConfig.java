package com.jskno.managinglistsbe.config;

import com.jskno.managinglistsbe.utils.logging.InlineQueryLogEntryCreator;
import com.p6spy.engine.spy.P6DataSource;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
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
public class DatasourceProxyTestConfig {

    @Value("${hibernate.hbm2ddl.auto}") String hbm2ddlType;
    @Value("${hibernate.dialect}") String dialect;
    @Value("${hibernate.show_sql}") boolean showSql;
    @Value("${hibernate.format_sql}") boolean formatSql;
    @Value("${hibernate.use_sql_comments}") boolean useSqlComments;

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");

        ChainListener listener = new ChainListener();
        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
        listener.addListener(loggingListener);
        listener.addListener(new DataSourceQueryCountListener());
        return ProxyDataSourceBuilder
                .create(dataSource)
                .name("DATA_SOURCE_PROXY")
                .listener(listener)
                .build();

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
        jpaProperties.put("hibernate.format_sql", formatSql);
        jpaProperties.put("hibernate.generate_statistics", true);

        // Hibernate disable BATCH FOR IDENTITY ID GENERATOR STRATEGY
        // https://stackoverflow.com/questions/27697810/hibernate-disabled-insert-batching-when-using-an-identity-identifier-generator
//        jpaProperties.put("hibernate.jdbc.batch_size", "5");
//        jpaProperties.put("hibernate.order_inserts", true);

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
