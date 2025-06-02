package com.extension.project.boundlessbooks.configuration;

import com.extension.project.boundlessbooks.interceptor.ApiKeyValidatorInterceptor;
import com.extension.project.boundlessbooks.interceptor.LoggerInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WebConfigurationIT {

    @Autowired
    private ApiKeyValidatorInterceptor apiKeyValidatorInterceptor;

    @Autowired
    private LoggerInterceptor loggerInterceptor;

    @Test
    void contextLoads() {
        assertThat(apiKeyValidatorInterceptor).isNotNull();
        assertThat(loggerInterceptor).isNotNull();
    }
}