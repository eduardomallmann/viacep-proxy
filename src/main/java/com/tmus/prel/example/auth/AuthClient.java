package com.tmus.prel.example.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AuthClient", url = "${app.auth.base-url}")
public interface AuthClient {

    @PostMapping(value = "${app.auth.path}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthToken getToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authorization,
                       @RequestHeader(AuthUtils.X_AUTHORIZATION) final String popToken);
}
