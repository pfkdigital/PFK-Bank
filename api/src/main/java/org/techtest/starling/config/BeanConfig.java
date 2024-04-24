package org.techtest.starling.config;

import io.micrometer.common.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// This class is a configuration class that defines a bean of type WebMvcConfigurer.
@Configuration
public class BeanConfig {

    // This method returns a WebMvcConfigurer object that is used to configure CORS.
    // It allows requests from http://localhost:3000 to access the API.
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry
                        .addMapping("/api/**")
                        .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }
}
