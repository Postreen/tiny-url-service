package ru.emobile.tinyurl.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.application.service.LinkApplicationService;
import ru.emobile.tinyurl.exception.ShortCodeAlreadyExistsException;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LinkApplicationService linkApplicationService;

    @Test
    @DisplayName("Создание ссылки возвращает 200 и данные созданной ссылки")
    void shouldCreateLinkSuccessfully() throws Exception {

        LinkResponse response = new LinkResponse(
                "https://google.com",
                "http://localhost:8080/abc123",
                "abc123",
                Instant.now(),
                null
        );

        when(linkApplicationService.create(any(LinkCreateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/links")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "url": "https://google.com",
                                          "shortCode": "abc123"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortCode")
                        .value("abc123"))
                .andExpect(jsonPath("$.originalUrl")
                        .value("https://google.com"));
    }

    @Test
    @DisplayName("Создание ссылки с пустым URL возвращает 400")
    void shouldReturnBadRequestWhenUrlIsEmpty() throws Exception {

        mockMvc.perform(
                        post("/api/v1/links")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "url": ""
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Создание ссылки с некорректным URL возвращает 400")
    void shouldReturn400WhenUrlInvalid() throws Exception {

        mockMvc.perform(post("/api/v1/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "url":"google"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Создание ссылки с существующим shortCode возвращает 409")
    void shouldReturn409WhenShortCodeExists() throws Exception {

        doThrow(
                new ShortCodeAlreadyExistsException("abc123")
        )
                .when(linkApplicationService)
                .create(any());


        mockMvc.perform(post("/api/v1/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "url":"https://google.com",
                                  "shortCode":"abc123"
                                }
                                """))
                .andExpect(status().isConflict());
    }
}
