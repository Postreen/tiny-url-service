package ru.emobile.tinyurl.domain.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;
import ru.emobile.tinyurl.exception.ShortCodeAlreadyExistsException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShortCodeResolver {

    private final LinkPersistenceService linkPersistenceService;
    private final ShortCodeGenerator generator;

    public String resolve(String requestedShortCode) {

        if (requestedShortCode != null) {
            log.debug("Custom shortCode requested. shortCode={}",
                    requestedShortCode
            );

            if (linkPersistenceService.existsByShortCode(requestedShortCode)) {
                log.warn("ShortCode already exists. shortCode={}",
                        requestedShortCode
                );

                throw new ShortCodeAlreadyExistsException(
                        requestedShortCode
                );
            }

            log.debug("Using custom shortCode. shortCode={}",
                    requestedShortCode
            );

            return requestedShortCode;
        }

        String generatedShortCode = generator.generate();

        log.debug("Generated new shortCode. shortCode={}",
                generatedShortCode
        );

        return generatedShortCode;
    }
}
