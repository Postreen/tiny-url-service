package ru.emobile.tinyurl.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.emobile.tinyurl.api.contract.LinkApi;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.application.service.LinkApplicationService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LinkController implements LinkApi {

    private final LinkApplicationService linkApplicationService;

    @Override
    public ResponseEntity<LinkResponse> create(LinkCreateRequest request) {
        log.info("Creating link. url={}, shortCode={}",
                request.url(),
                request.shortCode()
        );

        LinkResponse response = linkApplicationService.create(request);

        log.info("Link created. shortCode={}",
                response.shortCode()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
