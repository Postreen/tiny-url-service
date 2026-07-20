package ru.emobile.tinyurl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.exception.LinkNotFoundException;
import ru.emobile.tinyurl.repository.LinkRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository repository;

    @Override
    public LinkResponse create(LinkCreateRequest request) {
        Link link = new Link();
        link.setOriginalUrl(request.url());
        String code = generateCode();
        link.setShortCode(code);
        link.setAlias(request.alias());
        link.setExpiresAt(request.expiresAt());
        repository.save(link);

        return new LinkResponse(
                link.getId(),
                link.getOriginalUrl(),
                "http://localhost:8080/" + code,
                link.getShortCode(),
                link.getCreatedAt(),
                link.getExpiresAt()
        );
    }

    @Override
    public String getOriginalUrl(String code) {
        Link link = repository.findByShortCode(code)
                .orElseThrow(() -> new LinkNotFoundException(code));

        return link.getOriginalUrl();
    }

    private String generateCode() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 8);
    }
}
