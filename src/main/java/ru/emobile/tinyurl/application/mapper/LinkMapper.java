package ru.emobile.tinyurl.application.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.domain.entity.Link;

@Component
public class LinkMapper {

    private final String baseUrl;

    public LinkMapper(
            @Value("${app.base-url}") String baseUrl
    ) {
        this.baseUrl = baseUrl;
    }

    public LinkResponse toResponse(Link link) {
        return new LinkResponse(
                link.getOriginalUrl(),
                baseUrl + "/" + link.getShortCode(),
                link.getShortCode(),
                link.getCreatedAt(),
                link.getExpiresAt()
        );
    }
}
