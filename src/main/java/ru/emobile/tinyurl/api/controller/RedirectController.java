package ru.emobile.tinyurl.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.emobile.tinyurl.api.contract.RedirectApi;
import ru.emobile.tinyurl.application.service.LinkApplicationService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController implements RedirectApi {

    private final LinkApplicationService linkApplicationService;


    @Override
    public ResponseEntity<Void> redirect(String code) {

        String url = linkApplicationService.getOriginalUrl(code);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
