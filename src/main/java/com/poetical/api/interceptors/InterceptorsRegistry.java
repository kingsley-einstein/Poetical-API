package com.poetical.api.interceptors;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterceptorsRegistry implements WebMvcConfigurer {

    @Autowired
    Interceptors interceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptors);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
        .addMapping("*")
        .allowedOrigins("*")
        .allowedHeaders("Content-Type", "Accept", "Accept-Language", "Content-Language, Authorization")
        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
    }
}