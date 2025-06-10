package com.extension.project.boundlessbooks.service;

import com.extension.project.boundlessbooks.enums.InteractionType;
import com.extension.project.boundlessbooks.model.dto.InteractionResponse;

public interface InteractionService {
    InteractionResponse sendInteractionEvent(Long bookId, String userId, InteractionType interactionType);
}
