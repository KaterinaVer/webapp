package com.godeltech.mastery.task.config;

import com.godeltech.mastery.task.dao.EmployeeDao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@PropertySource("database.properties")
@PropertySource("sql.properties")
@Configuration
public class DaoConfiguration {

    @Bean
    public DataSource getDataSource() {
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("database.driverClassName");
        dataSource.setUrl("database.url");
        dataSource.setUsername("database.username");
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }
    @Bean
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDao(getDataSource());
    }
}
