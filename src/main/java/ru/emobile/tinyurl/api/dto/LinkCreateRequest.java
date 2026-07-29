package ru.emobile.tinyurl.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LinkCreateRequest(

        @NotBlank(message = "URL must not be empty")
        @Pattern(
                regexp = "^(https?|ftp)://.*$",
                message = "Invalid URL"
        )
        String url,

        @Size(
                min = 3,
                max = 20,
                message = "Short code length must be between 3 and 20 characters"
        )
        @Pattern(
                regexp = "^[a-zA-Z0-9_-]+$",
                message = "Short code contains invalid characters"
        )
        String shortCode,

        @Positive(message = "TTL must be positive")
        Long ttlMinutes
) {
}
