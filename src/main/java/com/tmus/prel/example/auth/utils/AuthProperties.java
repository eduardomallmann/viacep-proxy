package com.tmus.prel.example.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("app.auth")
public class AuthProperties {

    private String clientId;
    private String clientSecret;
    private String path;
    private String baseUrl;
    private PopTokenProperties poptoken;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PopTokenProperties {
        private String privateKey;
        private String privateKeyPass;
    }
}
