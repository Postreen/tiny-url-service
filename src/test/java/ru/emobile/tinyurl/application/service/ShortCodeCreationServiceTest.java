package ru.emobile.tinyurl.application.service;

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
class ShortCodeCreationServiceTest {

    @Mock
    private ShortCodeGenerator generator;

    @Mock
    private LinkPersistenceService persistenceService;

    @InjectMocks
    private ShortCodeCreationService service;


    @Test
    @DisplayName("Должен вернуть пользовательский shortCode, если он свободен")
    void shouldReturnCustomShortCode() {
        when(persistenceService.existsByShortCode("custom"))
                .thenReturn(false);

        String result = service.create("custom");

        assertThat(result).isEqualTo("custom");

        verify(persistenceService)
                .existsByShortCode("custom");
        verifyNoInteractions(generator);
    }

    @Test
    @DisplayName("Должен выбросить исключение, если пользовательский shortCode уже занят")
    void shouldThrowWhenCustomShortCodeExists() {
        when(persistenceService.existsByShortCode("custom"))
                .thenReturn(true);

        assertThatThrownBy(() -> service.create("custom"))
                .isInstanceOf(ShortCodeAlreadyExistsException.class);

        verifyNoInteractions(generator);
    }

    @Test
    @DisplayName("Должен сгенерировать новый shortCode, если пользовательский не указан")
    void shouldGenerateShortCodeWhenNotProvided() {
        when(generator.generate())
                .thenReturn("abc123");
        when(persistenceService.existsByShortCode("abc123"))
                .thenReturn(false);

        String result = service.create(null);

        assertThat(result).isEqualTo("abc123");

        verify(generator).generate();
    }

    @Test
    @DisplayName("Должен повторить генерацию shortCode при обнаружении коллизии")
    void shouldRetryWhenGeneratedShortCodeExists() {
        when(generator.generate())
                .thenReturn("abc123")
                .thenReturn("def456");

        when(persistenceService.existsByShortCode("abc123"))
                .thenReturn(true);

        when(persistenceService.existsByShortCode("def456"))
                .thenReturn(false);

        String result = service.create(null);

        assertThat(result).isEqualTo("def456");

        verify(generator, times(2)).generate();
    }

    @Test
    @DisplayName("Должен выбросить исключение после превышения количества попыток генерации")
    void shouldThrowWhenAllGeneratedCodesExist() {
        when(generator.generate())
                .thenReturn("abc123");

        when(persistenceService.existsByShortCode("abc123"))
                .thenReturn(true);

        assertThatThrownBy(() -> service.create(null))
                .isInstanceOf(ShortCodeAlreadyExistsException.class);

        verify(generator, times(5)).generate();
    }
}
