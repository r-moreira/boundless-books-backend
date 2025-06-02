package com.extension.project.boundlessbooks.aspect;

import com.extension.project.boundlessbooks.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OidcUserValidationAspect {

    @Before("""
        @annotation(com.extension.project.boundlessbooks.annotation.ValidateOidcUser) ||
        @within(com.extension.project.boundlessbooks.annotation.ValidateOidcUser)
    """)
    public void validateOidcUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof OidcUser)) {
            log.warn("Unauthorized access: OIDC user is null or invalid");
            throw new UnauthorizedException("Unauthorized: OIDC user is missing");
        }
    }
}