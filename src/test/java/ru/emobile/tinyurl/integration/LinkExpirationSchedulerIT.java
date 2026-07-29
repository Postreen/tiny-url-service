package ru.emobile.tinyurl.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.emobile.tinyurl.application.scheduler.LinkExpirationScheduler;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.entity.enums.Status;
import ru.emobile.tinyurl.repository.LinkRepository;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class LinkExpirationSchedulerIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private LinkExpirationScheduler scheduler;

    @Autowired
    private LinkRepository repository;

    @Test
    @DisplayName("Должен пометить истекшие ссылки как EXPIRED")
    void shouldMarkExpiredLinks() {

        Link link = new Link();

        link.setOriginalUrl("https://google.com");
        link.setShortCode("expired123");
        link.setExpiresAt(Instant.now().minusSeconds(3600));
        link.setStatus(Status.ACTIVE);

        repository.save(link);

        scheduler.expireLinks();

        Link updated = repository.findByShortCode("expired123")
                .orElseThrow();

        assertThat(updated.getStatus())
                .isEqualTo(Status.EXPIRED);
    }
}