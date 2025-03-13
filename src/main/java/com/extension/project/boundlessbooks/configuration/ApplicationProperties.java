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

    @Valid
    private OAuth2 oauth2;

    @Data
    @Validated
    public static class Flags {
        private boolean enableApiKeyValidation;
    }

    @Data
    @Validated
    public static class OAuth2 {
        private String redirectUri;
    }
}
