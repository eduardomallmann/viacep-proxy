package com.tmus.prel.example.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationException extends Exception {

    private final AuthError authError;

    public AuthenticationException(final String message) {
        super(message);
        log.error("message=Authentication Error thrown, message={}", message);
        this.authError = new ObjectMapper().convertValue(message, AuthError.class);
    }

    public AuthError getAuthError() {
        return authError;
    }
}
