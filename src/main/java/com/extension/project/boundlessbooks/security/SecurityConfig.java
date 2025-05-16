package com.extension.project.boundlessbooks.security;

import com.extension.project.boundlessbooks.configuration.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationProperties properties;
    private final AuthenticationSuccessHandler successHandler;

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(
            JdbcOperations jdbcOperations,
            ClientRegistrationRepository clientRegistrationRepository) {

        return new JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
         return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                .requestMatchers(
                        "/api/v1/books/**",
                        "/login/**",
                        "/favicon.ico",
                        "/actuator/**",
                        "/api/v1/users/search/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger-resources"
                ).permitAll()
                .anyRequest().authenticated())
            .oauth2Login(oauth2LoginCustomizer -> oauth2LoginCustomizer
                .loginPage("/oauth2/authorization/google") //TODO: Receber valor via property
                .successHandler(successHandler))
                .logout(logoutCustomizer -> logoutCustomizer
                .logoutUrl("/logout") //TODO: Receber valor via property
                .logoutSuccessUrl(properties.getOauth2().getRedirectUri())
                .invalidateHttpSession(true)
                .deleteCookies("BOUNDLESS_BOOKS_SESSION") //TODO: Receber valor via property
                .permitAll())
             .exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
                     .authenticationEntryPoint(authenticationEntryPoint))
            .build();
    }
}
