package ru.emobile.tinyurl.domain.expiration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class ExpirationCalculator {

    public Instant calculateExpiration(Long ttlMinutes) {

        if (ttlMinutes == null) {
            log.debug("TTL is not provided. Link will be permanent");
            return null;
        }

        Instant expiresAt = Instant.now()
                .plus(Duration.ofMinutes(ttlMinutes));

        log.debug("Expiration calculated. ttlMinutes={}, expiresAt={}",
                ttlMinutes,
                expiresAt
        );

        return expiresAt;
    }
}
