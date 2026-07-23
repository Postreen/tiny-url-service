package ru.emobile.tinyurl.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.expiration.LinkExpirationChecker;
import ru.emobile.tinyurl.domain.factory.LinkFactory;
import ru.emobile.tinyurl.exception.LinkExpiredException;
import ru.emobile.tinyurl.application.mapper.LinkMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkApplicationService {

    private final LinkPersistenceService linkPersistenceService;
    private final LinkExpirationChecker linkExpirationChecker;
    private final LinkFactory factory;
    private final LinkMapper mapper;

    @Transactional
    public LinkResponse create(LinkCreateRequest request) {

        Link link = factory.create(request);
        linkPersistenceService.save(link);

        return mapper.toResponse(link);
    }

    @Transactional(noRollbackFor = LinkExpiredException.class)
    public String getOriginalUrl(String code) {
        Link link = linkPersistenceService.findByShortCode(code);

        linkExpirationChecker.check(link);

        return link.getOriginalUrl();
    }
}