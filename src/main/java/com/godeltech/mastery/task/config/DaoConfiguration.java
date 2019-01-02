package com.godeltech.mastery.task.config;

import com.godeltech.mastery.task.dao.EmployeeDao;
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
public class DaoConfiguration {

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("${database.driverClassName}");
        dataSource.setUrl("${database.url}");
        dataSource.setUsername("${database.username}");
        dataSource.setPassword("${database.password}");

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

}


