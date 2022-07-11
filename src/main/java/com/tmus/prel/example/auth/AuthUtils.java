package com.tmus.prel.example.auth;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtils {

    private static final String BASIC_AUTH = "Basic";
    public static final String CONTENT_LENGTH = "0";
    public static final String X_AUTHORIZATION = "X-Authorization";

    private final AuthProperties authProperties;

    public String getBasicAuth() {
        log.info("M=getBasicAuth, message=Generating Basic Auth value");
        final String basicAuth = authProperties.getClientId() + ":" + authProperties.getClientSecret();
        final String encodedAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes());
        log.debug("M=getBasicAuth, message=Basic Auth value generated, value={}", encodedAuth);
        log.info("M=getBasicAuth, message=Basic Auth value successfully generated");
        return BASIC_AUTH + " " + encodedAuth;
    }
}
