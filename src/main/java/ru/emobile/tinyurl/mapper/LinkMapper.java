package ru.emobile.tinyurl.mapper;

import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.domain.entity.Link;

@Component
public class LinkMapper {

    public LinkResponse toResponse(Link link) {
        return new LinkResponse(
                link.getOriginalUrl(),
                "http://localhost:8080/" + link.getShortCode(),
                link.getShortCode(),
                link.getCreatedAt(),
                link.getExpiresAt()
        );
    }
}
