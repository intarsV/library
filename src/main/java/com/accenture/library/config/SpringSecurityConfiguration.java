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
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.authorities-query}")
    private String rolesQuery;

    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//need for React port 3000 - should be removed on prod
                .headers().frameOptions().disable()// need for H2 console - should be removed on prod
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
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }
}



