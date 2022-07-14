package com.tmus.prel.example.auth.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class AuthenticationException extends Exception {

    private final AuthError authError;

    public AuthenticationException(final Exception e) {
        super(e.getMessage());
        log.error("message=Authentication Error thrown, message={}", e.getMessage());
        this.authError = new AuthError(HttpStatus.INTERNAL_SERVER_ERROR.name(), null, e.getMessage(), null);
    }

    public AuthError getAuthError() {
        return authError;
    }
}
