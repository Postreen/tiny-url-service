package ru.emobile.tinyurl.domain.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UuidBase62GeneratorTest {

    private final UuidBase62Generator generator = new UuidBase62Generator();

    @Test
    @DisplayName("Должен генерировать непустой shortCode")
    void shouldGenerateNonNullCode() {
        String code = generator.generate();

        assertThat(code)
                .isNotNull()
                .isNotBlank();
    }

    @Test
    @DisplayName("Должен генерировать shortCode длиной 8 символов")
    void shouldGenerateCodeWithLengthEight() {
        String code = generator.generate();

        assertThat(code).hasSize(8);
    }

    @Test
    @DisplayName("Должен использовать только символы Base62")
    void shouldGenerateBase62CharactersOnly() {
        String code = generator.generate();

        assertThat(code).matches("^[0-9A-Za-z]{8}$");
    }

    @Test
    @DisplayName("Должен генерировать уникальные shortCode")
    void shouldGenerateUniqueCodes() {
        Set<String> codes = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            codes.add(generator.generate());
        }

        assertThat(codes).hasSize(1000);
    }
}
