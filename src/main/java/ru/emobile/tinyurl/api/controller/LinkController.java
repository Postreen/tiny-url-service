package ru.emobile.tinyurl.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.emobile.tinyurl.api.contract.LinkApi;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.application.service.LinkApplicationService;

@RestController
@RequiredArgsConstructor
public class LinkController implements LinkApi {

    private final LinkApplicationService linkApplicationService;

    @Override
    public LinkResponse create(LinkCreateRequest request) {
        return linkApplicationService.create(request);
    }
}
