package ru.emobile.tinyurl.domain.expiration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.entity.enums.Status;
import ru.emobile.tinyurl.exception.LinkExpiredException;

import java.time.Instant;

@Slf4j
@Component
public class LinkExpirationChecker {

    public void check(Link link) {

        log.debug("Checking link expiration. shortCode={}, expiresAt={}, status={}",
                link.getShortCode(),
                link.getExpiresAt(),
                link.getStatus()
        );

        if (link.getExpiresAt() == null) {
            return;
        }

        if (link.getStatus() == Status.EXPIRED) {
            log.warn("Link already expired. shortCode={}",
                    link.getShortCode()
            );

            throw new LinkExpiredException(link.getShortCode());
        }

        if (link.getExpiresAt().isBefore(Instant.now())) {

            log.warn("Link expired. Updating status. shortCode={}, previousStatus={}",
                    link.getShortCode(),
                    link.getStatus()
            );

            link.setStatus(Status.EXPIRED);

            throw new LinkExpiredException(link.getShortCode());
        }
    }
}
