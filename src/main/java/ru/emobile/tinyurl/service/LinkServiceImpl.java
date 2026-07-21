package ru.emobile.tinyurl.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.factory.LinkFactory;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;
import ru.emobile.tinyurl.exception.LinkNotFoundException;
import ru.emobile.tinyurl.mapper.LinkMapper;
import ru.emobile.tinyurl.repository.LinkRepository;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository repository;
    private final LinkMapper mapper;
    private final LinkFactory factory;

    @Transactional
    @Override
    public LinkResponse create(LinkCreateRequest request) {
        Link link = factory.create(request);
        repository.save(link);
        return mapper.toResponse(link);
    }

    @Override
    public String getOriginalUrl(String code) {
        Link link = repository.findByShortCode(code)
                .orElseThrow(() -> new LinkNotFoundException(code));

        return link.getOriginalUrl();
    }
}
