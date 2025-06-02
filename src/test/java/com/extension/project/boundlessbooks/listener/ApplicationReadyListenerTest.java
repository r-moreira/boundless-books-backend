package com.extension.project.boundlessbooks.listener;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApplicationReadyListenerTest {

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ApplicationProperties.Flags flags;

    @Mock
    private ApplicationReadyEvent event;

    @Mock
    private Environment environment;

    @InjectMocks
    private ApplicationReadyListener applicationReadyListener;

    @Test
    void onApplicationEvent_LogsFeatureFlags_H2() {
        when(applicationProperties.getFlags()).thenReturn(flags);
        when(flags.isEnableApiKeyValidation()).thenReturn(true);
        when(flags.toString()).thenReturn("ApplicationProperties.Flags(enableApiKeyValidation=true)");
        when(environment.getActiveProfiles()).thenReturn(new String[]{"h2"});

        try (LogCaptor logCaptor = LogCaptor.forClass(ApplicationReadyListener.class)) {
            applicationReadyListener.onApplicationEvent(event);

            assertThat(logCaptor.getInfoLogs())
                    .anyMatch(log -> log.contains("Application starting with database: H2, and feature flags: ApplicationProperties.Flags(enableApiKeyValidation=true)"));
        }
    }

    @Test
    void onApplicationEvent_LogsFeatureFlags_Postgresql() {
        when(applicationProperties.getFlags()).thenReturn(flags);
        when(flags.isEnableApiKeyValidation()).thenReturn(true);
        when(flags.toString()).thenReturn("ApplicationProperties.Flags(enableApiKeyValidation=true)");
        when(environment.getActiveProfiles()).thenReturn(new String[]{"postgresql"});

        try (LogCaptor logCaptor = LogCaptor.forClass(ApplicationReadyListener.class)) {
            applicationReadyListener.onApplicationEvent(event);

            assertThat(logCaptor.getInfoLogs())
                    .anyMatch(log -> log.contains("Application starting with database: PostgreSQL, and feature flags: ApplicationProperties.Flags(enableApiKeyValidation=true)"));
        }
    }
}