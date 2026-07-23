package ru.emobile.tinyurl.domain.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.expiration.ExpirationCalculator;
import ru.emobile.tinyurl.domain.resolver.ShortCodeResolver;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkFactoryTest {
    @Mock
    private ExpirationCalculator expirationCalculator;

    @Mock
    private ShortCodeResolver shortCodeResolver;

    @InjectMocks
    private LinkFactory factory;


    @Test
    @DisplayName("Должен создать Link с пользовательским URL, shortCode и временем жизни")
    void shouldCreateLinkWithExpiration() {
        LinkCreateRequest request = new LinkCreateRequest(
                "https://google.com",
                "google",
                60L
        );

        Instant expiresAt = Instant.parse("2026-07-23T15:00:00Z");

        when(shortCodeResolver.resolve("google")).thenReturn("google");
        when(expirationCalculator.calculateExpiration(60L)).thenReturn(expiresAt);

        Link link = factory.create(request);

        assertThat(link.getOriginalUrl()).isEqualTo("https://google.com");
        assertThat(link.getShortCode()).isEqualTo("google");
        assertThat(link.getExpiresAt()).isEqualTo(expiresAt);

        verify(shortCodeResolver).resolve("google");
        verify(expirationCalculator).calculateExpiration(60L);
    }

    @Test
    @DisplayName("Должен создать вечную ссылку если TTL не указан")
    void shouldCreatePermanentLinkWithoutExpiration() {
        LinkCreateRequest request = new LinkCreateRequest(
                "https://google.com",
                null,
                null
        );

        when(shortCodeResolver.resolve(null)).thenReturn("abc123");
        when(expirationCalculator.calculateExpiration(null)).thenReturn(null);

        Link link = factory.create(request);

        assertThat(link.getOriginalUrl()).isEqualTo("https://google.com");
        assertThat(link.getShortCode()).isEqualTo("abc123");
        assertThat(link.getExpiresAt()).isNull();

        verify(shortCodeResolver).resolve(null);
        verify(expirationCalculator).calculateExpiration(null);
    }

    @Test
    @DisplayName("Должен передавать shortCode из resolver, а не использовать значение напрямую")
    void shouldUseResolvedShortCode() {
        LinkCreateRequest request = new LinkCreateRequest(
                "https://example.com",
                "custom",
                null
        );

        when(shortCodeResolver.resolve("custom"))
                .thenReturn("generated");

        Link link = factory.create(request);

        assertThat(link.getShortCode()).isEqualTo("generated");

        verify(shortCodeResolver).resolve("custom");
    }
}
