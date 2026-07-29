package ru.emobile.tinyurl.util;

import ru.emobile.tinyurl.domain.command.CreateLinkCommand;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.entity.enums.Status;

import java.time.Instant;


public final class LinkTestFactory {

    private static final String DEFAULT_URL = "https://google.com";
    private static final String DEFAULT_CODE = "abc123";
    private static final Long DEFAULT_TTL = 60L;

    private LinkTestFactory() {
    }

    public static Link defaultLink() {
        return createLink(
                DEFAULT_URL,
                DEFAULT_CODE,
                Status.ACTIVE,
                null
        );
    }

    public static Link activePermanentLink() {
        return createLink(
                DEFAULT_URL,
                DEFAULT_CODE,
                Status.ACTIVE,
                null
        );
    }

    public static Link activeLinkWithExpiration() {
        return createLink(
                DEFAULT_URL,
                DEFAULT_CODE,
                Status.ACTIVE,
                Instant.now().plusSeconds(3600)
        );
    }

    public static Link expiredLink() {
        return createLink(
                DEFAULT_URL,
                DEFAULT_CODE,
                Status.EXPIRED,
                Instant.now().plusSeconds(3600)
        );
    }

    public static Link linkWithExpiredDate() {
        return createLink(
                DEFAULT_URL,
                DEFAULT_CODE,
                Status.ACTIVE,
                Instant.now().minusSeconds(3600)
        );
    }

    public static Link linkWithCode(String code) {
        return createLink(
                DEFAULT_URL,
                code,
                Status.ACTIVE,
                null
        );
    }


    public static CreateLinkCommand defaultCreateCommand() {
        return new CreateLinkCommand(
                DEFAULT_URL,
                DEFAULT_CODE,
                DEFAULT_TTL
        );
    }

    public static CreateLinkCommand permanentCreateCommand() {
        return new CreateLinkCommand(
                DEFAULT_URL,
                DEFAULT_CODE,
                null
        );
    }

    private static Link createLink(
            String url,
            String shortCode,
            Status status,
            Instant expiresAt
    ) {
        Link link = new Link();

        link.setOriginalUrl(url);
        link.setShortCode(shortCode);
        link.setStatus(status);
        link.setExpiresAt(expiresAt);

        return link;
    }
}

