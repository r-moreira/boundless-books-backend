package com.extension.project.boundlessbooks.configuration;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

    ApplicationProperties applicationProperties;
}
