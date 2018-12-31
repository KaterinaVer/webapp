package com.godeltech.mastery.task.config;

import com.godeltech.mastery.task.dao.EmployeeDao;
import com.godeltech.mastery.task.service.EmployeeService;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@PropertySources({
        @PropertySource("database.properties"),
        @PropertySource("sql.properties")
})
public class ServiceTestConfiguration {

    @Bean
    public DataSource getDataSource() throws IOException {
        Resource createScript = new ClassPathResource("create-table.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(createScript);
        DatabasePopulatorUtils.execute(databasePopulator, embeddedPostgres().getPostgresDatabase());

        Resource insertScript = new ClassPathResource("insert-values.sql");
        databasePopulator = new ResourceDatabasePopulator(insertScript);
        DatabasePopulatorUtils.execute(databasePopulator, embeddedPostgres().getPostgresDatabase());
        return embeddedPostgres().getPostgresDatabase();
    }

    @Bean
    public EmbeddedPostgres embeddedPostgres() throws IOException {
        return EmbeddedPostgres.start();
    }

    @Bean
    public PlatformTransactionManager txManager() throws IOException {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public EmployeeDao getEmployeeDao() throws IOException {
        return new EmployeeDao(getDataSource());
    }

    @Bean
    public EmployeeService getEmployeeService() throws IOException {
        return new EmployeeService(getEmployeeDao());
    }
}
