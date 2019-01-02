package com.godeltech.mastery.task.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.*;

import java.io.IOException;

@Configuration
@ComponentScan("com.godeltech.mastery.task.service")
@Import(DaoTestConfiguration.class)
public class ServiceIntegrationTestConfiguration {

    @Bean
    public EmbeddedPostgres embeddedPostgres() throws IOException {
        return EmbeddedPostgres.start();
    }

}
