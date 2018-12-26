package com.godeltech.mastery.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("src/test/resources/database.properties"),
        @PropertySource("src/test/resources/sql.properties")
})
public class DaoConfiguration {
    
}
