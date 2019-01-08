package com.godeltech.mastery.task.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"com.godeltech.mastery.task.rest","com.godeltech.mastery.task.config"})
public class AppTestConfiguration {
}
