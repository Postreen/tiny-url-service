package ru.emobile.tinyurl.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;
import ru.emobile.tinyurl.exception.ShortCodeAlreadyExistsException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortCodeCreationService {

    private static final int MAX_SHORT_CODE_GENERATION_ATTEMPTS = 5;

    private final ShortCodeGenerator generator;
    private final LinkPersistenceService persistenceService;

    public String create(String requestedShortCode) {
        if (hasCustomShortCode(requestedShortCode)) {
            validateUnique(requestedShortCode);
            return requestedShortCode;
        }
        return generateUnique();
    }

    private String generateUnique() {

        for (int attempt = 1; attempt <= MAX_SHORT_CODE_GENERATION_ATTEMPTS; attempt++) {
            String generated = generator.generate();

            if (!persistenceService.existsByShortCode(generated)) {
                return generated;
            }

            log.warn("Generated shortCode collision. attempt={}", attempt);
        }
        throw new ShortCodeAlreadyExistsException();
    }

    private void validateUnique(String shortCode) {
        if (persistenceService.existsByShortCode(shortCode)) {
            throw new ShortCodeAlreadyExistsException(shortCode);
        }
    }

    private boolean hasCustomShortCode(String shortCode) {
        return shortCode != null && !shortCode.isBlank();
    }
}