package ru.emobile.tinyurl.domain.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;
import ru.emobile.tinyurl.exception.ShortCodeAlreadyExistsException;

@Component
@RequiredArgsConstructor
public class ShortCodeResolver {

    private final LinkPersistenceService linkPersistenceService;
    private final ShortCodeGenerator generator;

    public String resolve(String requestedShortCode) {

        if (requestedShortCode != null) {
            if (linkPersistenceService.existsByShortCode(requestedShortCode)) {
                throw new ShortCodeAlreadyExistsException(requestedShortCode);
            }

            return requestedShortCode;
        }

        return generator.generate();
    }
}
