package ru.emobile.tinyurl.application.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.emobile.tinyurl.repository.LinkRepository;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkExpirationScheduler {

    private final LinkRepository repository;

    @Scheduled(fixedRateString = "${app.scheduler.link-expiration.fixed-rate}")
    @Transactional
    public void expireLinks() {
        int count = repository.expireLinks(Instant.now());

        log.info("Expired links updated: {}", count);
    }
}
