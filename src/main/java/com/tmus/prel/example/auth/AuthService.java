package com.tmus.prel.example.auth;

import com.tmobile.security.taap.poptoken.builder.exception.PopTokenBuilderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PopTokenService popTokenService;
    private final AuthClient authClient;
    private final AuthUtils authUtils;

    public AuthToken getToken() throws PopTokenBuilderException, AuthenticationException {
        log.info("M=getToken, message=Start retrieving authentication token");
        final String popToken = popTokenService.generatePopToken();
        final String basicAuth = authUtils.getBasicAuth();
        try {
            final var authToken = authClient.getToken(basicAuth, popToken);
            log.info("M=getToken, message=Authentication token successfully retrieved, authToken={}", authToken);
            return authToken;
        } catch (Exception e) {
            log.error("M=getToken, message=Authentication token failed, exception={}", e.getMessage());
            throw new AuthenticationException(e.getMessage());
        }
    }
}
