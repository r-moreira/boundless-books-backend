package com.extension.project.boundlessbooks.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InteractionType {
    READ_START("read_start"),
    READ_END("read_end"),
    FAVORITE_ADD("favorite_add"),
    FAVORITE_REMOVE("favorite_remove"),
    SHELF_ADD("shelf_add"),
    SHELF_REMOVE("shelf_remove");

    @JsonValue
    private final String value;

    @JsonCreator
    public static InteractionType fromValue(String value) {
        for (InteractionType type : InteractionType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown interaction type: " + value);
    }
}
