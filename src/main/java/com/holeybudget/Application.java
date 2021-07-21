package com.holeybudget;

import com.holeybudget.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import(AppConfig.class)
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class Application {
    public static void main(String[] args) {
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println(args.length);
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        SpringApplication.run(Application.class, args);

        SpringApplication application = new SpringApplication(Application.class);
        application.setAddCommandLineProperties(false);
        application.run(args);
    }
}