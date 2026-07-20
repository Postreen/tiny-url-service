package ru.emobile.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.emobile.tinyurl.exception.dto.ErrorResponse;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLinkNotFound(
            LinkNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}
