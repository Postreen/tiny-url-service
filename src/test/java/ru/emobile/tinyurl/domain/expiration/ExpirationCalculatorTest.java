package ru.emobile.tinyurl.domain.expiration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ExpirationCalculatorTest {

    private final ExpirationCalculator calculator = new ExpirationCalculator();

    @Test
    @DisplayName("Должен вернуть null, если TTL не указан")
    void shouldReturnNullWhenTtlIsNull() {
        Instant expiresAt = calculator.calculateExpiration(null);

        assertThat(expiresAt).isNull();
    }

    @Test
    @DisplayName("Должен корректно вычислить дату истечения срока действия ссылки")
    void shouldCalculateExpirationTime() {
        Instant before = Instant.now();

        Instant expiresAt = calculator.calculateExpiration(60L);

        Instant after = Instant.now();

        assertThat(expiresAt)
                .isAfterOrEqualTo(before.plus(Duration.ofMinutes(60)))
                .isBeforeOrEqualTo(after.plus(Duration.ofMinutes(60)));
    }

    @Test
    @DisplayName("Должен вычислить дату истечения для TTL равного нулю")
    void shouldCalculateExpirationWhenTtlIsZero() {
        Instant before = Instant.now();

        Instant expiresAt = calculator.calculateExpiration(0L);

        Instant after = Instant.now();

        assertThat(expiresAt)
                .isAfterOrEqualTo(before)
                .isBeforeOrEqualTo(after);
    }
}
