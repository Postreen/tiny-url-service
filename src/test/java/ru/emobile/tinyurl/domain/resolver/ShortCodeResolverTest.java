package ru.emobile.tinyurl.domain.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;
import ru.emobile.tinyurl.exception.ShortCodeAlreadyExistsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortCodeResolverTest {

    @Mock
    private LinkPersistenceService linkPersistenceService;

    @Mock
    private ShortCodeGenerator generator;

    @InjectMocks
    private ShortCodeResolver resolver;

    @Test
    @DisplayName("Должен вернуть пользовательский shortCode, если он свободен")
    void shouldReturnRequestedShortCodeWhenAvailable() {
        String shortCode = "google";

        when(linkPersistenceService.existsByShortCode(shortCode))
                .thenReturn(false);

        String result = resolver.resolve(shortCode);

        assertThat(result)
                .isEqualTo(shortCode);

        verify(linkPersistenceService)
                .existsByShortCode(shortCode);

        verifyNoInteractions(generator);
    }


    @Test
    @DisplayName("Должен выбросить исключение, если пользовательский shortCode уже существует")
    void shouldThrowExceptionWhenRequestedShortCodeAlreadyExists() {
        String shortCode = "google";

        when(linkPersistenceService.existsByShortCode(shortCode))
                .thenReturn(true);

        assertThatThrownBy(() -> resolver.resolve(shortCode))
                .isInstanceOf(ShortCodeAlreadyExistsException.class)
                .hasMessage("Alias already exists: google");

        verify(linkPersistenceService)
                .existsByShortCode(shortCode);

        verifyNoInteractions(generator);
    }


    @Test
    @DisplayName("Должен сгенерировать shortCode, если пользовательский не указан")
    void shouldGenerateShortCodeWhenRequestedShortCodeIsNull() {
        String generatedCode = "aB12Cd34";

        when(generator.generate())
                .thenReturn(generatedCode);

        String result = resolver.resolve(null);

        assertThat(result).isEqualTo(generatedCode);

        verify(generator).generate();

        verifyNoInteractions(linkPersistenceService);
    }
}
