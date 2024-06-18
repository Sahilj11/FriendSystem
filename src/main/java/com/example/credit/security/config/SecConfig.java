package com.example.credit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * SecConfig
 */
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.credit.security.config.handlers.CustomAuthSuccessHandler;
import com.example.credit.security.config.handlers.CustomLogoutHandler;

@Configuration
public class SecConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(req -> req.requestMatchers("/auth/signup").permitAll()
                .requestMatchers("/css/**").permitAll()
                .anyRequest().hasAuthority("FREE"));

        http.formLogin(
                login -> login.loginPage("/auth/login").usernameParameter("email").loginProcessingUrl("/auth/login")
                        .permitAll().successHandler(new CustomAuthSuccessHandler()));
        http.logout(logout -> logout
                .logoutSuccessHandler(new CustomLogoutHandler()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
