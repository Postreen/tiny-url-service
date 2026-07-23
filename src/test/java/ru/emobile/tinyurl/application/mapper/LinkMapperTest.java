package ru.emobile.tinyurl.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.util.LinkTestFactory;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class LinkMapperTest {

    private final LinkMapper mapper = new LinkMapper();

    @Test
    @DisplayName("Маппинг Link в LinkResponse для ссылки с временем окончания")
    void shouldMapLinkWithExpirationToResponse() {

        Link link = LinkTestFactory.activeLinkWithExpiration();

        LinkResponse response = mapper.toResponse(link);

        assertThat(response.originalUrl()).isEqualTo("https://google.com");
        assertThat(response.shortCode()).isEqualTo("abc123");
        assertThat(response.shortUrl()).isEqualTo("http://localhost:8080/abc123");
        assertThat(response.expiresAt()).isNotNull();
    }

    @Test
    @DisplayName("Маппинг Link в LinkResponse для постоянной ссылки")
    void shouldMapPermanentLinkToResponse() {

        Link link = LinkTestFactory.activePermanentLink();

        LinkResponse response = mapper.toResponse(link);

        assertThat(response.originalUrl()).isEqualTo("https://google.com");
        assertThat(response.shortCode()).isEqualTo("abc123");
        assertThat(response.shortUrl()).isEqualTo("http://localhost:8080/abc123");
        assertThat(response.expiresAt()).isNull();
    }
}
