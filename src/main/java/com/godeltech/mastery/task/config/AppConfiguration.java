package com.godeltech.mastery.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.godeltech.mastery.task.config",
        "com.godeltech.mastery.task.dao",
        "com.godeltech.mastery.task.service",
        "com.godeltech.mastery.task.rest"})
public class AppConfiguration {

}