package ru.ya.spring3pw.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.ya.spring3pw")
@PropertySource("classpath:application.properties")
public class WebConfiguration {}