package ru.emobile.tinyurl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.factory.LinkFactory;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;
import ru.emobile.tinyurl.exception.LinkNotFoundException;
import ru.emobile.tinyurl.exception.ShortCodeAlreadyExistsException;
import ru.emobile.tinyurl.mapper.LinkMapper;
import ru.emobile.tinyurl.repository.LinkRepository;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository repository;
    private final LinkMapper mapper;
    private final LinkFactory factory;
    private final ShortCodeGenerator generator;

    @Transactional
    @Override
    public LinkResponse create(LinkCreateRequest request) {
        String shortCode = resolveShortCode(request);
        Link link = factory.create(request, shortCode);
        repository.save(link);

        return mapper.toResponse(link);
    }

    @Override
    public String getOriginalUrl(String code) {

        Link link = repository.findByShortCode(code)
                .orElseThrow(() -> new LinkNotFoundException(code));

        return link.getOriginalUrl();
    }

    private String resolveShortCode(LinkCreateRequest request) {
        if (request.shortCode() != null) {
            if (repository.existsByShortCode(request.shortCode())) {
                throw new ShortCodeAlreadyExistsException(
                        request.shortCode()
                );
            }
            return request.shortCode();
        }
        return generator.generate();
    }

}
