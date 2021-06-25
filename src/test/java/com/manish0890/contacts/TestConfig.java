package com.manish0890.contacts;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan
@PropertySources({@PropertySource("classpath:application.properties"), @PropertySource("classpath:test.properties")})
@EnableAutoConfiguration
public class TestConfig {
}
