package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.enums.InteractionType;
import com.extension.project.boundlessbooks.exception.NotImplementedException;
import com.extension.project.boundlessbooks.model.dto.InteractionEvent;
import com.extension.project.boundlessbooks.model.dto.InteractionResponse;
import com.extension.project.boundlessbooks.service.InteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {
    private final ApplicationProperties applicationProperties;
    private final KafkaTemplate<String, InteractionEvent> kafkaTemplate;
    private final Environment environment;

    public InteractionResponse sendInteractionEvent(
            Long bookId,
            String userId,
            InteractionType interactionType) {
        if (!Arrays.stream(environment.getActiveProfiles()).toList().contains("kafka")) {
            throw new NotImplementedException("Kafka profile needs to be activated");
        }

        var processId = UUID.randomUUID().toString();

        InteractionEvent event = new InteractionEvent(
                processId,
                bookId.toString(),
                userId,
                interactionType,
                Date.from(Instant.now())
        );

        log.info("Sending interaction event to Kafka: {}", event);

        kafkaTemplate.send(applicationProperties.getKafka().getTopicName(), event);

        log.info("Interaction event sent successfully, processId={}, userId={}, bookId={}, interactionType={}",
                processId, userId, bookId, interactionType);
        return new InteractionResponse(processId);
    }
}