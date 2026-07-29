package ru.emobile.tinyurl.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.emobile.tinyurl.api.contract.RedirectApi;
import ru.emobile.tinyurl.application.service.LinkApplicationService;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController implements RedirectApi {

    private final LinkApplicationService linkApplicationService;

    @Override
    public ResponseEntity<Void> redirect(String code) {
        log.info("Redirect request received. shortCode={}", code);

        String url = linkApplicationService.getOriginalUrl(code);

        log.info("Redirect successful. shortCode={}, targetUrl={}",
                code,
                url
        );

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
