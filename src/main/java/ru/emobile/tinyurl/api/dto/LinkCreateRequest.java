package ru.emobile.tinyurl.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LinkCreateRequest(
        @NotBlank(message = "URL must not be empty")
        @Pattern(
                regexp = "^(https?|ftp)://.*$",
                message = "Invalid URL"
        )
        String url,

        String shortCode,
        Long ttlMinutes
) {
}
