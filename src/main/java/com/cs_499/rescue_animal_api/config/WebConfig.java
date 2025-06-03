package com.cs_499.rescue_animal_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow CORS for the batch upload endpoint, fetch all dogs endpoint, and fetch all monkeys endpoint
                registry.addMapping("/api/v1/animals/dogs")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET")
                        .allowedHeaders("*");
                registry.addMapping("/api/v1/animals/monkeys")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET")
                        .allowedHeaders("*");
                registry.addMapping("/api/v1/animals/batch-upload")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}