package com.extension.project.boundlessbooks.cronjob;

import com.extension.project.boundlessbooks.enums.InteractionType;
import com.extension.project.boundlessbooks.service.InteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("kafka")
@ConditionalOnProperty(name = "application.flags.enable-mock-interactions-event", havingValue = "true")
public class RandomInteractionEventSender {
    private final InteractionService interactionService;
    private final Random random = new Random();

    @Scheduled(fixedRate = 500)
    public void sendRandomInteraction() {
        Long userId = random.nextLong(5) + 1;
        Long bookId = random.nextLong(17) + 1;
        InteractionType interactionType = InteractionType.values()[random.nextInt(InteractionType.values().length)];

        log.trace("Sending random interaction: userId={}, bookId={}, interactionType={}", userId, bookId, interactionType);

        interactionService.sendInteractionEvent(bookId, userId.toString(), interactionType);

    }
}