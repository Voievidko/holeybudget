package com.yourfounds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= "com.yourfounds")
public class AppConfig {

    // define a bean for ViewResolver
    @Bean
    public ViewResolver viewResolver() {

        var viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
