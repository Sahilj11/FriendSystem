package com.example.credit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** CorsConfig */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //@Bean
    //public CorsFilter corsFilter() {
    //    UrlBasedCorsConfigurationSource config = new UrlBasedCorsConfigurationSource();
    //    CorsConfiguration corsConfiguration = new CorsConfiguration();
    //    corsConfiguration.setAllowCredentials(true);
    //    corsConfiguration.addAllowedOrigin("http://localhost:5173");
    //    corsConfiguration.addExposedHeader("Authorization");
    //    corsConfiguration.addExposedHeader("Content-Type");
    //    corsConfiguration.addAllowedHeader("GET");
    //    corsConfiguration.addAllowedHeader("POST");
    //    corsConfiguration.setMaxAge(3600L);
    //    config.registerCorsConfiguration("/**", corsConfiguration);
    //    return new CorsFilter(config);
    //}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600L);
    }
}
