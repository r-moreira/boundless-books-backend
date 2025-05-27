package com.extension.project.boundlessbooks.interceptor;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeyValidatorInterceptor implements HandlerInterceptor {

    public static final String X_API_KEY_HEADER = "X-API-KEY";
    private final ApplicationProperties applicationProperties;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        if (!applicationProperties.getFlags().isEnableApiKeyValidation()) {
            return true;
        }

        if (request.getRequestURI().contains("/books")
                || request.getRequestURI().contains("/users")) {

            String apiKey = request.getHeader(X_API_KEY_HEADER);

            if (apiKey == null || !apiKey.equals(applicationProperties.getApiKey())) {
                throw new UnauthorizedException("Invalid API Key");
            }
        }

        return true;
    }
}
