package com.extension.project.boundlessbooks.configuration.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String apiKey;

    @Valid
    private Flags flags;

    @Valid
    private OAuth2 oauth2;

    @Valid
    private Kafka kafka;

    @Data
    public static class Flags {
        private boolean enableApiKeyValidation;
        private boolean enableMockInteractionsEvent;
    }

    @Data
    public static class OAuth2 {

        @NotBlank
        private String redirectUri;

        @NotBlank
        private String loginPath;

        @NotBlank
        private String logoutPath;
    }

    @Data
    public static class Kafka {

        @NotBlank
        private String topicName;

        private String enabled;

        @Valid
        private MockInteractions mockInteractions;

        @Data
        public static class MockInteractions {
            private boolean enabled;
        }
    }
}