package ru.emobile.tinyurl.domain.factory;

import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.domain.entity.Link;

@Component
public class LinkFactory {

    public Link create(LinkCreateRequest request, String shortCode) {

        Link link = new Link();

        link.setOriginalUrl(request.url());
        link.setShortCode(shortCode);
        link.setExpiresAt(request.expiresAt());

        return link;
    }
}
