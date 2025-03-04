package com.extension.project.boundlessbooks.configuration;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String apiKey;

    @Valid
    private Flags flags;

    @Data
    @Validated
    public static class Flags {
        private boolean enableApiKeyValidation;
    }
}
