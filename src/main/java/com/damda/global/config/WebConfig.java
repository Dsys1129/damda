package com.damda.global.config;

import com.damda.global.auth.resolver.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Value("${upload.feeds.path}")
    private String feedsPath;

    @Value("${upload.users.path}")
    private String usersPath;

    @Value("${upload.couples.path}")
    private String couplesPath;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/couples_image/**")
                .addResourceLocations(couplesPath);
        registry.addResourceHandler("/users_image/**")
                .addResourceLocations(usersPath);
        registry.addResourceHandler("/feeds_image/**")
                .addResourceLocations(feedsPath);
    }
}
