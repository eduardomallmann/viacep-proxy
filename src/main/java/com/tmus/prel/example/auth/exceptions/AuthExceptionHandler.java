package com.tmus.prel.example.auth.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler, responsible to catch Auth exceptions launched from the controller and treat as per application convenience.
 *
 * @author 013087631
 */
@Slf4j
@ControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles authentication exception response to the origin.
     *
     * @param ex exception thrown
     *
     * @return the exceptions in a error message standard inside a response entity.
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<AuthError> handleAuthenticationExceptions(final AuthenticationException ex) {
        log.error("M=handleAuthenticationExceptions, message=Start handling AuthenticationException, error={}", ex.getAuthError());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(ex.getAuthError());
    }

}
