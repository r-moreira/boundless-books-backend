package com.extension.project.boundlessbooks.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Component
@ConfigurationProperties(prefix = "server.servlet.session.cookie")
public class ServerCookieProperties {

    @NotBlank
    private String name;
}