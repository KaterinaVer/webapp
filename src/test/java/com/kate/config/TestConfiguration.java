package com.kate.config;

import com.kate.dao.EmployeeDao;
import com.kate.dao.EmployeeDaoImpl;
import com.kate.service.EmployeeService;
import com.kate.service.EmployeeServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

import java.io.IOException;

@Configuration
@PropertySources({
        @PropertySource("database.properties"),
        @PropertySource("sql.properties")
})
public class TestConfiguration {

    @Bean
    public DataSource dataSource() throws IOException{
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
    public EmployeeDao employeeDao() throws IOException{
        return new EmployeeDaoImpl(dataSource());
    }

    @Bean
    public EmployeeService employeeService() throws IOException{
        return new EmployeeServiceImpl(employeeDao());
    }

    @Bean
    public PlatformTransactionManager txManager() throws IOException{
        return new DataSourceTransactionManager(dataSource());
    }

}
