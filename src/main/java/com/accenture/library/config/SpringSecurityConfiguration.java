package com.accenture.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.authorities-query}")
    private String rolesQuery;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    DataSource dataSource;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()//allow CORS option calls
                .antMatchers(HttpMethod.GET, "/basicauth").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/v1/authors").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/authors").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/v1/authors/**").hasAnyAuthority(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/v1/books/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/books").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/v1/books/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/v1/reservations").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/reservations").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/reservations/**").authenticated()
                .and()
                .httpBasic();
    }
}



