package com.extension.project.boundlessbooks.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggerInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @InjectMocks
    private LoggerInterceptor interceptor;

    @Test
    void preHandle_LogsRequestDetails() {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameterMap()).thenReturn(Collections.singletonMap("param1", new String[]{"value1"}));
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singleton("Header1")));
        when(request.getHeader("Header1")).thenReturn("HeaderValue1");

        boolean result = interceptor.preHandle(request, response, handler);

        verify(request, times(1)).getRequestURL();
        verify(request, times(1)).getMethod();
        verify(request, times(1)).getParameterMap();
        verify(request, times(1)).getHeaderNames();
        verify(request, times(1)).getHeader("Header1");

        assert result;
    }

    @Test
    void preHandle_NoHeadersOrParams_LogsMinimalRequestDetails() {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameterMap()).thenReturn(Collections.emptyMap());
        when(request.getHeaderNames()).thenReturn(Collections.emptyEnumeration());

        boolean result = interceptor.preHandle(request, response, handler);

        verify(request, times(1)).getRequestURL();
        verify(request, times(1)).getMethod();
        verify(request, times(1)).getParameterMap();
        verify(request, times(1)).getHeaderNames();

        assert result;
    }

    @Test
    void preHandle_LogsRequestDetails_withCaptor() {
        LogCaptor logCaptor = LogCaptor.forClass(LoggerInterceptor.class);

        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameterMap()).thenReturn(Collections.singletonMap("param1", new String[]{"value1"}));
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singleton("Header1")));
        when(request.getHeader("Header1")).thenReturn("HeaderValue1");

        interceptor.preHandle(request, response, handler);

        assertThat(logCaptor.getInfoLogs())
                .anyMatch(log -> log.contains("URL=http://localhost/test") &&
                        log.contains("Method=GET") &&
                        log.contains("param1: value1") &&
                        log.contains("Header1: HeaderValue1"));

        logCaptor.close();
    }
}