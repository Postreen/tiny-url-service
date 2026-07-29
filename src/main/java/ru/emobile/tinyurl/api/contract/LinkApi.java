package ru.emobile.tinyurl.api.contract;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.emobile.tinyurl.api.dto.LinkCreateRequest;
import ru.emobile.tinyurl.api.dto.LinkResponse;

@RequestMapping("/api/v1/links")
public interface LinkApi {

    @Operation(
            summary = "Create short link",
            description = "Creates a new short URL"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Link successfully created"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Short code already exists"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            )
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<LinkResponse> create(
            @Valid @RequestBody LinkCreateRequest request
    );
}