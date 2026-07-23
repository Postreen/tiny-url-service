package ru.emobile.tinyurl.api.contract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
public interface RedirectApi {

    @Operation(
            summary = "Redirect by short code",
            description = "Redirects user to original URL"
    )
    @ApiResponse(
            responseCode = "302",
            description = "Redirect successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Link not found"
    )
    @ApiResponse(
            responseCode = "410",
            description = "Link expired"
    )
    @GetMapping("/{code}")
    ResponseEntity<Void> redirect(
            @PathVariable String code
    );
}
