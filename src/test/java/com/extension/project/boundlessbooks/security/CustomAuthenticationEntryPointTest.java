package com.extension.project.boundlessbooks.security;

import com.extension.project.boundlessbooks.model.dto.HttpErrorBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationEntryPointTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private CustomAuthenticationEntryPoint entryPoint;

    @Test
    void commence_WritesUnauthorizedErrorResponse() throws Exception {
        when(authException.getMessage()).thenReturn("Invalid token");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        HttpErrorBody expectedErrorBody = new HttpErrorBody(
                "Unauthorized access",
                "Forbidden",
                HttpServletResponse.SC_UNAUTHORIZED
        );
        when(objectMapper.writeValueAsString(expectedErrorBody)).thenReturn("{\"message\":\"Unauthorized access\",\"error\":\"Forbidden\",\"status\":401}");

        entryPoint.commence(request, response, authException);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String actualResponse = stringWriter.toString();
        String expectedResponse = objectMapper.writeValueAsString(expectedErrorBody);

        assertEquals(expectedResponse, actualResponse);
    }
}