package ru.emobile.tinyurl.service;

import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;

public interface LinkService {
    LinkResponse create(LinkCreateRequest request);

    String getOriginalUrl(String code);
}
