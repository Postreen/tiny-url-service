package ru.emobile.tinyurl.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.emobile.tinyurl.application.service.LinkApplicationService;
import ru.emobile.tinyurl.exception.LinkExpiredException;
import ru.emobile.tinyurl.exception.LinkNotFoundException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RedirectController.class)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LinkApplicationService linkApplicationService;

    @Test
    @DisplayName("Редирект по короткому коду возвращает 302 и Location")
    void shouldRedirectToOriginalUrl() throws Exception {

        when(linkApplicationService.getOriginalUrl("abc123"))
                .thenReturn("https://google.com");

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header()
                        .string(
                                "Location",
                                "https://google.com"
                        ));
    }

    @Test
    @DisplayName("Редирект по несуществующему shortCode возвращает 404")
    void shouldReturn404WhenLinkNotFound() throws Exception {

        when(linkApplicationService.getOriginalUrl("abc123"))
                .thenThrow(new LinkNotFoundException("abc123"));

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Link not found: abc123"));
    }

    @Test
    @DisplayName("Редирект по истекшей ссылке возвращает 410")
    void shouldReturn410WhenLinkExpired() throws Exception {

        when(linkApplicationService.getOriginalUrl("abc123"))
                .thenThrow(new LinkExpiredException("abc123"));

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isGone())
                .andExpect(jsonPath("$.message")
                        .value("Link expired: abc123"));
    }
}
