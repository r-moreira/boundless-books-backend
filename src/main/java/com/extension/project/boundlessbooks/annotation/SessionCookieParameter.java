package com.extension.project.boundlessbooks.annotation;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        name = "BOUNDLESS_BOOKS_SESSION",
        description = "Session cookie",
        example = "MGY3OGUz",
        in = ParameterIn.COOKIE,
        required = true,
        schema = @Schema(type = "string", example = "MGY3OGUz")
)
public @interface SessionCookieParameter {
}
