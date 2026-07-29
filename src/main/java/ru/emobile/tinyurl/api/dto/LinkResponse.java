package ru.emobile.tinyurl.api.dto;

import java.time.Instant;

public record LinkResponse(
        String originalUrl,
        String shortUrl,
        String shortCode,
        Instant createdAt,
        Instant expiresAt
) {
}
