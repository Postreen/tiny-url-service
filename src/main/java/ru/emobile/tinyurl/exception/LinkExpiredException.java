package ru.emobile.tinyurl.exception;

public class LinkExpiredException extends RuntimeException {

    public LinkExpiredException(String code) {
        super("Link expired: " + code);
    }
}
