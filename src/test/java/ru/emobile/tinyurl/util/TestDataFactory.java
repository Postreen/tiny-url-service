package ru.emobile.tinyurl.util;

import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;

import java.time.Instant;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static LinkCreateRequest defaultRequest() {
        return new LinkCreateRequest(
                "https://google.com",
                null,
                null
        );
    }

    public static LinkCreateRequest requestWithShortCode() {
        return new LinkCreateRequest(
                "https://google.com",
                "google",
                null
        );
    }

    public static LinkCreateRequest requestWithTtl() {
        return new LinkCreateRequest(
                "https://google.com",
                null,
                60L
        );
    }

    public static LinkCreateRequest requestWithShortCodeAndTtl() {
        return new LinkCreateRequest(
                "https://google.com",
                "google",
                60L
        );
    }


    public static LinkResponse defaultLinkResponse() {
        return new LinkResponse(
                "https://google.com",
                "http://localhost:8080/abc123",
                "abc123",
                Instant.now(),
                null
        );
    }
}
