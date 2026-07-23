package ru.emobile.tinyurl.domain.expiration;

import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.entity.enums.Status;
import ru.emobile.tinyurl.exception.LinkExpiredException;

import java.time.Instant;

@Component
public class LinkExpirationChecker {

    public void check(Link link) {

        if (link.getExpiresAt() == null) {
            return;
        }

        if (link.getStatus() == Status.EXPIRED) {
            throw new LinkExpiredException(link.getShortCode());
        }

        if (link.getExpiresAt().isBefore(Instant.now())) {
            link.setStatus(Status.EXPIRED);

            throw new LinkExpiredException(link.getShortCode());
        }
    }
}
