package ru.emobile.tinyurl.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;
import ru.emobile.tinyurl.service.LinkService;

@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public LinkResponse create(
            @Valid @RequestBody LinkCreateRequest request
    ) {
        return linkService.create(request);
    }
}
