package ru.emobile.tinyurl.application.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.exception.LinkNotFoundException;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.repository.LinkRepository;

@Component
@RequiredArgsConstructor
public class LinkPersistenceService {

    private final LinkRepository repository;

    public boolean existsByShortCode(String code) {
        return repository.existsByShortCode(code);
    }

    public void save(Link link) {
        repository.save(link);
    }

    public Link findByShortCode(String code) {
        return repository.findByShortCode(code)
                .orElseThrow(() -> new LinkNotFoundException(code));
    }
}
