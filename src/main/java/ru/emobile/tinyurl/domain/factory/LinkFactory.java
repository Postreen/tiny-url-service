package ru.emobile.tinyurl.domain.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.expiration.ExpirationCalculator;
import ru.emobile.tinyurl.domain.resolver.ShortCodeResolver;

@Component
@RequiredArgsConstructor
public class LinkFactory {
    private final ExpirationCalculator expirationCalculator;
    private final ShortCodeResolver shortCodeResolver;

    public Link create(LinkCreateRequest request) {
        String shortCode = shortCodeResolver.resolve(request.shortCode());

        Link link = new Link();

        link.setOriginalUrl(request.url());
        link.setShortCode(shortCode);

        link.setExpiresAt(
                expirationCalculator.calculateExpiration(
                        request.ttlMinutes()
                )
        );

        return link;
    }
}
