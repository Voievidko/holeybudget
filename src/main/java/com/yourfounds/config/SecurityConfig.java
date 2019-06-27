package com.yourfounds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserBuilder users = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles("USER"))
                .withUser(users.username("mary").password("test123").roles("MANAGER"))
                .withUser(users.username("roman").password("test123").roles("ADMIN"));
    }

    // Configure security of web paths in application, login, logout etc
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // Restrict access based on HttpServletRequest
                    .anyRequest().authenticated() // Any request to the app must be logged in
                .and()
                .formLogin() // Customize for for login
                    .loginPage("/login")
                    // No controller request mapping for this
                    .loginProcessingUrl("/authenticate") // Login form should POST data to this URL
                    .permitAll(); // Allow everyone to see login page
    }
}