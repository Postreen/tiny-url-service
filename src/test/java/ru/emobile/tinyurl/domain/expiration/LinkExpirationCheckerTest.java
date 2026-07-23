package ru.emobile.tinyurl.domain.expiration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.emobile.tinyurl.util.LinkTestFactory;
import ru.emobile.tinyurl.domain.entity.Link;
import ru.emobile.tinyurl.domain.entity.enums.Status;
import ru.emobile.tinyurl.exception.LinkExpiredException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinkExpirationCheckerTest {
    private final LinkExpirationChecker checker = new LinkExpirationChecker();

    @Test
    @DisplayName("Должен пропустить ссылку без срока действия")
    void shouldPassWhenExpirationIsNull() {
        Link link = LinkTestFactory.activePermanentLink();

        checker.check(link);

        assertThat(link.getStatus())
                .isEqualTo(Status.ACTIVE);
    }

    @Test
    @DisplayName("Должен пропустить активную ссылку с неистекшим сроком действия")
    void shouldPassWhenLinkIsActive() {
        Link link = LinkTestFactory.activeLinkWithExpiration();

        checker.check(link);

        assertThat(link.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    @DisplayName("Должен выбросить исключение для уже просроченной ссылки")
    void shouldThrowExceptionWhenStatusIsExpired() {
        Link link = LinkTestFactory.expiredLink();

        assertThatThrownBy(() -> checker.check(link))
                .isInstanceOf(LinkExpiredException.class)
                .hasMessage("Link expired: abc123");
    }

    @Test
    @DisplayName("Должен изменить статус на EXPIRED и выбросить исключение, если срок действия ссылки истек")
    void shouldExpireLinkWhenExpirationTimePassed() {
        Link link = LinkTestFactory.linkWithExpiredDate();

        assertThatThrownBy(() -> checker.check(link))
                .isInstanceOf(LinkExpiredException.class)
                .hasMessage("Link expired: abc123");

        assertThat(link.getStatus()).isEqualTo(Status.EXPIRED);
    }
}
