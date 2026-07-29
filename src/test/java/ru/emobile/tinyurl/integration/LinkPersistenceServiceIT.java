package ru.emobile.tinyurl.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.exception.LinkNotFoundException;
import ru.emobile.tinyurl.util.LinkTestFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class LinkPersistenceServiceIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17");

    @Autowired
    private LinkPersistenceService persistenceService;


    @Test
    @DisplayName("Должен сохранить ссылку и найти ее по shortCode")
    void shouldSaveAndFindLinkByShortCode() {

        Link link = LinkTestFactory.linkWithCode("test123");

        persistenceService.save(link);

        Link result = persistenceService.findByShortCode("test123");

        assertThat(result.getShortCode()).isEqualTo("test123");

        assertThat(result.getOriginalUrl()).isEqualTo("https://google.com");
    }


    @Test
    @DisplayName("Должен выбросить ошибку если ссылка не найдена")
    void shouldThrowExceptionWhenLinkNotFound() {

        assertThatThrownBy(
                () -> persistenceService.findByShortCode("unknown")
        )
                .isInstanceOf(LinkNotFoundException.class);
    }

    @Test
    @DisplayName("Должен вернуть true если shortCode существует")
    void shouldReturnTrueWhenShortCodeExists() {

        Link link = LinkTestFactory.linkWithCode("exists123");

        persistenceService.save(link);

        boolean exists = persistenceService.existsByShortCode("exists123");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Должен вернуть false если shortCode отсутствует")
    void shouldReturnFalseWhenShortCodeNotExists() {

        boolean exists = persistenceService.existsByShortCode("unknown");

        assertThat(exists).isFalse();
    }
}
