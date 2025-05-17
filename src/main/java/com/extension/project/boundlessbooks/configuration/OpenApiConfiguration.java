package com.extension.project.boundlessbooks.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Boundless Books Backend", version = "0.0.1-SNAPSHOT"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server")
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "Application Key",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "X-API-KEY"
        )
})
public class OpenApiConfiguration {
}
