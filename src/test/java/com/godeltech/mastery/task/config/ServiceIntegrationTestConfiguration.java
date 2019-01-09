package com.godeltech.mastery.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.godeltech.mastery.task.service")
@Import(DaoTestConfiguration.class)
public class ServiceIntegrationTestConfiguration {

}
