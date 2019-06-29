package com.yourfounds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserBuilder users = User.withDefaultPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser(users.username("john").password("test123").roles("USER"))
//                .withUser(users.username("mary").password("test123").roles("MANAGER"))
//                .withUser(users.username("roman").password("test123").roles("USER", "ADMIN"));
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from `user` where username=?")
                .authoritiesByUsernameQuery("select username, authority from authority where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // Restrict access based on HttpServletRequest
                .antMatchers("/").hasRole("USER")
                .antMatchers("/user/all").hasRole("ADMIN")
                .and()
                    .formLogin() // Customize for for login
                    .loginPage("/login")
                    // No controller request mapping for this
                    .loginProcessingUrl("/authenticate") // Login form should POST data to this URL
                    .permitAll() // Allow everyone to see login page
                .and()
                    .logout().permitAll() // Allow everyone to logout
                .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");
    }
}