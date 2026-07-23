package ru.emobile.tinyurl.domain.expiration;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class ExpirationCalculator {

    public Instant calculateExpiration(Long ttlMinutes) {

        if (ttlMinutes == null) {
            return null;
        }

        return Instant.now().plus(Duration.ofMinutes(ttlMinutes));
    }
}
