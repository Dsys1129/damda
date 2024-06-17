package com.damda.global.config;


import com.damda.global.auth.filter.AuthExceptionFilter;
import com.damda.global.auth.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){

        FilterRegistrationBean<AuthenticationFilter> authentication = new FilterRegistrationBean<>();
        authentication.setFilter(new AuthenticationFilter());
        authentication.addUrlPatterns("/*");
        authentication.setOrder(2);
        return authentication;
    }

    @Bean
    public FilterRegistrationBean<AuthExceptionFilter> authExceptionFilter() {
        FilterRegistrationBean<AuthExceptionFilter> authException = new FilterRegistrationBean<>();
        authException.setFilter(new AuthExceptionFilter());
        authException.addUrlPatterns("/*");
        authException.setOrder(1);
        return authException;
    }
}