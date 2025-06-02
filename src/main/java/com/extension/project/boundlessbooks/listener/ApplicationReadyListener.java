package com.extension.project.boundlessbooks.listener;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationProperties applicationProperties;

    private final Environment environment;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        log.info("Application starting with database: {}, and feature flags: {}",
                Arrays.stream(environment.getActiveProfiles()).toList().contains("h2")
                        ? "H2"
                        : "PostgreSQL",
                applicationProperties.getFlags().toString()
        );
    }
}
