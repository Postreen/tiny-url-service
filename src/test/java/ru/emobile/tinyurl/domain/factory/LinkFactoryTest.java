package ru.emobile.tinyurl.domain.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.emobile.tinyurl.domain.command.CreateLinkCommand;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.expiration.ExpirationCalculator;
import ru.emobile.tinyurl.util.LinkTestFactory;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkFactoryTest {

    @Mock
    private ExpirationCalculator expirationCalculator;

    @InjectMocks
    private LinkFactory factory;

    @Test
    @DisplayName("Должен создать ссылку с указанным URL, shortCode и временем окончания")
    void shouldCreateLinkWithExpiration() {
        CreateLinkCommand command = LinkTestFactory.defaultCreateCommand();

        Instant expiresAt = Instant.parse("2026-07-23T15:00:00Z");

        when(expirationCalculator.calculateExpiration(60L))
                .thenReturn(expiresAt);

        Link link = factory.create(command);

        assertThat(link.getOriginalUrl()).isEqualTo("https://google.com");
        assertThat(link.getShortCode()).isEqualTo("abc123");
        assertThat(link.getExpiresAt()).isEqualTo(expiresAt);

        verify(expirationCalculator).calculateExpiration(60L);
    }

    @Test
    @DisplayName("Должен создать ссылку без срока действия, если TTL не указан")
    void shouldCreatePermanentLinkWithoutExpiration() {
        CreateLinkCommand command = LinkTestFactory.permanentCreateCommand();

        when(expirationCalculator.calculateExpiration(null))
                .thenReturn(null);

        Link link = factory.create(command);

        assertThat(link.getOriginalUrl()).isEqualTo("https://google.com");
        assertThat(link.getShortCode()).isEqualTo("abc123");
        assertThat(link.getExpiresAt()).isNull();

        verify(expirationCalculator).calculateExpiration(null);
    }
}