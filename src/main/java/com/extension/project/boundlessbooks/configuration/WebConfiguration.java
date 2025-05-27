package com.extension.project.boundlessbooks.configuration;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.interceptor.ApiKeyValidatorInterceptor;
import com.extension.project.boundlessbooks.interceptor.LoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final ApplicationProperties properties;
    private final ApiKeyValidatorInterceptor apiKeyValidatorInterceptor;
    private final LoggerInterceptor loggerInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getOauth2().getRedirectUri())
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor).order(1);
        registry.addInterceptor(apiKeyValidatorInterceptor).order(2);
    }
}
