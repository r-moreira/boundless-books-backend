package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.enums.InteractionType;
import com.extension.project.boundlessbooks.exception.NotImplementedException;
import com.extension.project.boundlessbooks.model.dto.InteractionEvent;
import com.extension.project.boundlessbooks.model.dto.InteractionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class InteractionServiceImplTest {

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ApplicationProperties.Kafka kafkaProperties;

    @Mock
    private KafkaTemplate<String, InteractionEvent> kafkaTemplate;

    @Mock
    private Environment environment;

    @InjectMocks
    private InteractionServiceImpl interactionService;

    @BeforeEach
    void setUp() {
        when(applicationProperties.getKafka()).thenReturn(kafkaProperties);
        when(applicationProperties.getKafka().getTopicName()).thenReturn("test-topic");
    }

    @Test
    void sendInteractionEvent_WithKafkaProfile_SendsEvent() {
        when(environment.getActiveProfiles()).thenReturn(new String[]{"kafka"});

        Long bookId = 1L;
        String userId = "user123";
        InteractionType interactionType = InteractionType.FAVORITE_ADD;

        InteractionResponse response = interactionService.sendInteractionEvent(bookId, userId, interactionType);

        assertNotNull(response);
        assertNotNull(response.processId());
        verify(kafkaTemplate, times(1)).send(eq("test-topic"), any(InteractionEvent.class));
    }

    @Test
    void sendInteractionEvent_WithoutKafkaProfile_ThrowsNotImplementedException() {
        when(environment.getActiveProfiles()).thenReturn(new String[]{"default"});

        Long bookId = 1L;
        String userId = "user123";
        InteractionType interactionType = InteractionType.READ_START;

        NotImplementedException exception = assertThrows(NotImplementedException.class, () ->
                interactionService.sendInteractionEvent(bookId, userId, interactionType)
        );

        assertEquals("Kafka profile needs to be activated", exception.getMessage());
        verify(kafkaTemplate, never()).send(anyString(), any(InteractionEvent.class));
    }

    @Test
    void sendInteractionEvent_CreatesCorrectInteractionEvent() {
        when(environment.getActiveProfiles()).thenReturn(new String[]{"kafka"});

        Long bookId = 1L;
        String userId = "user123";
        InteractionType interactionType = InteractionType.SHELF_ADD;

        interactionService.sendInteractionEvent(bookId, userId, interactionType);

        verify(kafkaTemplate, times(1)).send(eq("test-topic"), argThat(event -> {
            assertNotNull(event);
            assertEquals(bookId.toString(), event.bookId());
            assertEquals(userId, event.userId());
            assertEquals(interactionType, event.interactionType());
            assertNotNull(event.timestamp());
            assertNotNull(event.processId());
            return true;
        }));
    }
}