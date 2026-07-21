package ru.emobile.tinyurl.domain.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;

@Component
@RequiredArgsConstructor
public class LinkFactory {
    private final ShortCodeGenerator generator;

    public Link create(LinkCreateRequest request) {

        Link link = new Link();

        link.setOriginalUrl(request.url());
        link.setAlias(request.alias());
        link.setExpiresAt(request.expiresAt());
        link.setShortCode(generator.generate());

        return link;
    }
}
