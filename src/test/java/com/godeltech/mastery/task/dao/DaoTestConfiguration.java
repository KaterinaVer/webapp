package com.godeltech.mastery.task.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySources({
        @PropertySource("database.properties"),
        @PropertySource("sql.properties")
})
public class DaoTestConfiguration {

    @Value("${database.driverClassName}")
    String driverClassName;
    @Value("${database.url}")
    String url;
    @Value("${database.username}")
    String username;
    @Value("${database.password}")
    String password;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("database.driverClassName");
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        Resource createScript = new ClassPathResource("create-table.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(createScript);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        Resource insertScript = new ClassPathResource("insert-values.sql");
        databasePopulator = new ResourceDatabasePopulator(insertScript);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return dataSource;
    }
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
    @Bean
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDao(getDataSource());
    }
}
