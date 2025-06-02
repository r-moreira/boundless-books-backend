package com.extension.project.boundlessbooks.configuration;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.configuration.properties.ServerCookieProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationConfigurationIT {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ServerCookieProperties serverCookieProperties;

    @Test
    void contextLoads() {
        assertThat(applicationProperties).isNotNull();
        assertThat(serverCookieProperties).isNotNull();
    }
}