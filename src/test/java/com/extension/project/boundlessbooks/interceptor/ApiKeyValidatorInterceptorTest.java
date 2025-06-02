package com.extension.project.boundlessbooks.interceptor;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApiKeyValidatorInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ApplicationProperties.Flags flags;

    @InjectMocks
    private ApiKeyValidatorInterceptor interceptor;

    @BeforeEach
    void setUp() {
        when(applicationProperties.getFlags()).thenReturn(flags);
        when(applicationProperties.getApiKey()).thenReturn("valid-api-key");
    }

    @Test
    void preHandle_ApiKeyValidationDisabled_ReturnsTrue() {
        boolean result = interceptor.preHandle(request, response, handler);

        verifyNoInteractions(request);
        assert result;
    }

    @Test
    void preHandleBooks_ValidApiKey_ReturnsTrue() {
        when(applicationProperties.getFlags().isEnableApiKeyValidation()).thenReturn(true);
        when(request.getRequestURI()).thenReturn("/books");
        when(request.getHeader(ApiKeyValidatorInterceptor.X_API_KEY_HEADER)).thenReturn("valid-api-key");

        boolean result = interceptor.preHandle(request, response, handler);

        assert result;
    }

    @Test
    void preHandleUsers_ValidApiKey_ReturnsTrue() {
        when(applicationProperties.getFlags().isEnableApiKeyValidation()).thenReturn(true);
        when(request.getRequestURI()).thenReturn("/users");
        when(request.getHeader(ApiKeyValidatorInterceptor.X_API_KEY_HEADER)).thenReturn("valid-api-key");

        boolean result = interceptor.preHandle(request, response, handler);

        assert result;
    }

    @Test
    void preHandle_InvalidApiKey_ThrowsUnauthorizedException() {
        when(applicationProperties.getFlags().isEnableApiKeyValidation()).thenReturn(true);
        when(request.getRequestURI()).thenReturn("/books");
        when(request.getHeader(ApiKeyValidatorInterceptor.X_API_KEY_HEADER)).thenReturn("invalid-api-key");

        assertThrows(UnauthorizedException.class, () -> interceptor.preHandle(request, response, handler));
    }

    @Test
    void preHandle_MissingApiKey_ThrowsUnauthorizedException() {
        when(applicationProperties.getFlags().isEnableApiKeyValidation()).thenReturn(true);
        when(request.getRequestURI()).thenReturn("/books");
        when(request.getHeader(ApiKeyValidatorInterceptor.X_API_KEY_HEADER)).thenReturn(null);

        assertThrows(UnauthorizedException.class, () -> interceptor.preHandle(request, response, handler));
    }

    @Test
    void preHandle_NonProtectedEndpoint_ReturnsTrue() {
        applicationProperties.getFlags().setEnableApiKeyValidation(true);
        when(request.getRequestURI()).thenReturn("/public");

        boolean result = interceptor.preHandle(request, response, handler);

        assert result;
    }
}