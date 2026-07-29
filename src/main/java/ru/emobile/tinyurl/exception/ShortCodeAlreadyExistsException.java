package ru.emobile.tinyurl.exception;

public class ShortCodeAlreadyExistsException extends RuntimeException {

    public ShortCodeAlreadyExistsException(String shortCode) {
        super("Alias already exists: " + shortCode);
    }

    public ShortCodeAlreadyExistsException() {
        super("Could not generate unique short code");
    }
}
