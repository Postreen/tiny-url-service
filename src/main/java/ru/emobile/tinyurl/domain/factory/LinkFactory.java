package ru.emobile.tinyurl.domain.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.domain.command.CreateLinkCommand;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.expiration.ExpirationCalculator;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkFactory {

    private final ExpirationCalculator expirationCalculator;

    public Link create(CreateLinkCommand command) {

        Link link = new Link();

        link.setOriginalUrl(command.originalUrl());
        link.setShortCode(command.shortCode());

        link.setExpiresAt(
                expirationCalculator.calculateExpiration(
                        command.ttlMinutes()
                )
        );

        log.debug(
                "Link entity created. shortCode={}, expiresAt={}",
                link.getShortCode(),
                link.getExpiresAt()
        );

        return link;
    }
}