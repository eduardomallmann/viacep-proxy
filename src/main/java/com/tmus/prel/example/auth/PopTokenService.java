package com.tmus.prel.example.auth;

import com.tmobile.security.taap.poptoken.builder.PopEhtsKey;
import com.tmobile.security.taap.poptoken.builder.PopTokenBuilder;
import com.tmobile.security.taap.poptoken.builder.exception.PopTokenBuilderException;
import com.tmus.prel.example.auth.utils.AuthProperties;
import com.tmus.prel.example.auth.utils.AuthUtils;
import java.util.LinkedHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PopTokenService {

    private final AuthProperties authProperties;
    private final AuthUtils authUtils;

    public String generatePopToken() throws PopTokenBuilderException {
        log.info("M=generatePopToken, message=Starting Poptoken generation");
        final var popToken = PopTokenBuilder.newInstance()
                                     .setEhtsKeyValueMap(getPopTokenHeaders())
                                     .signWith(authProperties.getPoptoken().getPrivateKey())
                                     .build();
        log.info("M=generatePopToken, message=Poptoken successfully generated, poptoken={}", popToken);
        return popToken;
    }

    private LinkedHashMap<String, String> getPopTokenHeaders() {
        log.info("M=getPopTokenHeaders, message=Starting Poptoken headers generation");
        LinkedHashMap<String, String> ehtsKeyValueMap = new LinkedHashMap<>();
        ehtsKeyValueMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ehtsKeyValueMap.put(HttpHeaders.AUTHORIZATION, authUtils.getBasicAuth());
        ehtsKeyValueMap.put(PopEhtsKey.URI.keyName(), authProperties.getPath());
        ehtsKeyValueMap.put(PopEhtsKey.HTTP_METHOD.keyName(), HttpMethod.POST.name());
        log.info("M=getPopTokenHeaders, message=Poptoken headers generated, poptokenHeaders={}", ehtsKeyValueMap);
        return ehtsKeyValueMap;
    }


}
