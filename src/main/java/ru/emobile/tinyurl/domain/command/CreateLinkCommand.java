package ru.emobile.tinyurl.domain.command;

public record CreateLinkCommand(
        String originalUrl,
        String shortCode,
        Long ttlMinutes
) {
}
