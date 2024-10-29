package com.extension.project.boundlessbooks.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    AuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                .requestMatchers("/api/v1/books/**", "login/**").permitAll()
                .anyRequest().authenticated())
            .oauth2Login(oauth2LoginCustomizer -> oauth2LoginCustomizer
            .successHandler((request, response, authentication) -> {
                if (authentication.getPrincipal() instanceof DefaultOAuth2User userDetails) {
                    userDetails.getAttributes().forEach((key, value) -> log.info("Key: {}, Value: {}", key, value));
                    //TODO: salvar attr "name", "email" e "sub" (Primary Key) no banco de dados
                }
                response.sendRedirect("/api/v1/users/me");
            }))
            .build();
    }
}
