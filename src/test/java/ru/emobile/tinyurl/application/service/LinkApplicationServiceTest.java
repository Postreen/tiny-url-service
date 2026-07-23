package ru.emobile.tinyurl.application.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.emobile.tinyurl.util.LinkTestFactory;
import ru.emobile.tinyurl.util.TestDataFactory;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.application.mapper.LinkMapper;
import ru.emobile.tinyurl.application.persistence.LinkPersistenceService;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.expiration.LinkExpirationChecker;
import ru.emobile.tinyurl.domain.factory.LinkFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkApplicationServiceTest {

    @Mock
    private LinkPersistenceService linkPersistenceService;

    @Mock
    private LinkExpirationChecker linkExpirationChecker;

    @Mock
    private LinkFactory factory;

    @Mock
    private LinkMapper mapper;

    @InjectMocks
    private LinkApplicationService service;

    @Test
    @DisplayName("Должен создать ссылку и вернуть DTO")
    void shouldCreateLink() {
        LinkCreateRequest request = TestDataFactory.defaultRequest();

        Link link = LinkTestFactory.defaultLink();

        LinkResponse response = TestDataFactory.defaultLinkResponse();

        when(factory.create(request)).thenReturn(link);
        when(mapper.toResponse(link)).thenReturn(response);

        LinkResponse result = service.create(request);

        assertThat(result).isEqualTo(response);

        verify(factory).create(request);
        verify(linkPersistenceService).save(link);
        verify(mapper).toResponse(link);
    }

    @Test
    @DisplayName("Должен вернуть оригинальный URL активной ссылки")
    void shouldReturnOriginalUrlWhenLinkActive() {
        String code = "abc123";

        Link link = LinkTestFactory.defaultLink();

        when(linkPersistenceService.findByShortCode(code))
                .thenReturn(link);

        String result = service.getOriginalUrl(code);

        assertThat(result).isEqualTo("https://google.com");

        verify(linkPersistenceService).findByShortCode(code);
        verify(linkExpirationChecker).check(link);
    }

    @Test
    @DisplayName("Не должен возвращать URL если ссылка просрочена")
    void shouldThrowExceptionWhenLinkExpired() {
        String code = "expired";

        Link link = new Link();

        when(linkPersistenceService.findByShortCode(code))
                .thenReturn(link);

        doThrow(RuntimeException.class)
                .when(linkExpirationChecker)
                .check(link);

        assertThatThrownBy(() -> service.getOriginalUrl(code))
                .isInstanceOf(RuntimeException.class);

        verify(linkExpirationChecker).check(link);
    }
}