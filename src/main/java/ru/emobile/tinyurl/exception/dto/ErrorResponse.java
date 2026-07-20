package ru.emobile.tinyurl.exception.dto;

import java.time.Instant;

public record ErrorResponse(
        String message,
        Instant timestamp
) {
}
