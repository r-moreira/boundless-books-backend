package com.extension.project.boundlessbooks.factory;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class RequestFactory {

    public static MockHttpServletRequestBuilder build(MockHttpServletRequestBuilder method) {
        method.header("X-API-KEY", "123");

        return method;
    }
}