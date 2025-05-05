package com.extension.project.boundlessbooks.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        StringBuilder logMessage = new StringBuilder("Request Info: ");
        logMessage.append("URL=").append(request.getRequestURL()).append(", ");
        logMessage.append("Method=").append(request.getMethod()).append(", ");
        logMessage.append("Params={");
        request.getParameterMap().forEach((key, value) ->
                logMessage.append(key)
                        .append(": ")
                        .append(String.join(", ", value))
                        .append("; "));
        logMessage.append("}, Headers={");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logMessage.append(headerName)
                    .append(": ")
                    .append(request.getHeader(headerName))
                    .append("; ");
        }
        logMessage.append("}");

        log.info(logMessage.toString());

        return true;
    }
}