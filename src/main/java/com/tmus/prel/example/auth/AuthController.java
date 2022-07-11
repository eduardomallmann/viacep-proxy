package com.tmus.prel.example.auth;

import com.tmobile.security.taap.poptoken.builder.exception.PopTokenBuilderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final PopTokenService popTokenService;
    private final AuthService authService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AuthToken retrieveAuth() throws PopTokenBuilderException, AuthenticationException {
        log.info("M=retrieveAuth, message=Authorization token retrieval request received");
        final var authToken = authService.getToken();
        log.info("M=retrieveAuth, message=Authorization token successfully retrieved, authToken={}", authToken);
        return authToken;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/poptoken")
    public String retrievePopToken() throws PopTokenBuilderException {
        log.info("M=retrievePopToken, message=Poptoken retrieval request received");
        final var poptoken = popTokenService.generatePopToken();
        log.info("M=retrievePopToken, message=Poptoken successfully retrieved, poptoken={}", poptoken);
        return poptoken;
    }

}

